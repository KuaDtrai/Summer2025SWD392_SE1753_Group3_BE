package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.SeatRequest;
import com.project.ibtss.dto.response.SeatResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.SeatMapper;
import com.project.ibtss.model.Buses;
import com.project.ibtss.model.Seats;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.repository.SeatRepository;
import com.project.ibtss.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;
    private final SeatMapper seatMapper;

    @Override
    public List<SeatResponse> createSeats(List<SeatRequest> requests) {
        return requests.stream().map(req -> {
            if (seatRepository.existsByBusIdAndSeatCodeIgnoreCase(req.getBusId(), req.getSeatCode())) {
                throw new AppException(ErrorCode.INVALID_KEY);
            }
            Buses bus = busRepository.findById(req.getBusId())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            long currentSeatCount = seatRepository.countByBusId(req.getBusId());
            if (currentSeatCount >= bus.getSeatCount()) {
                throw new AppException(ErrorCode.INVALID_KEY);
            }
            Seats seat = seatMapper.toEntity(req);
            seat.setBus(bus);
            seat.setCreatedAt(LocalDateTime.now());
            seat.setUpdatedAt(LocalDateTime.now());
            return seatMapper.toResponse(seatRepository.save(seat));
        }).collect(Collectors.toList());
    }

    @Override
    public List<SeatResponse> autoGenerateSeats(Integer busId) {
        Buses bus = busRepository.findById(busId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        int seatCount = bus.getSeatCount();
        List<SeatResponse> result = new ArrayList<>();

        for (int i = 1; i <= seatCount; i++) {
            String seatCode = "S" + i;
            if (seatRepository.existsByBusIdAndSeatCodeIgnoreCase(busId, seatCode)) continue;

            Seats seat = new Seats();
            seat.setSeatCode(seatCode);
            seat.setBus(bus);
            seat.setCreatedAt(LocalDateTime.now());
            seat.setUpdatedAt(LocalDateTime.now());
            result.add(seatMapper.toResponse(seatRepository.save(seat)));
        }
        return result;
    }

    @Override
    public SeatResponse updateSeat(Integer id, SeatRequest request) {
        Seats seat = seatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!seat.getSeatCode().equalsIgnoreCase(request.getSeatCode()) &&
                seatRepository.existsByBusIdAndSeatCodeIgnoreCase(request.getBusId(), request.getSeatCode())) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        Buses bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        long currentSeatCount = seatRepository.countByBusId(request.getBusId());
        boolean isChangingBus = !seat.getBus().getId().equals(bus.getId());
        if (isChangingBus && currentSeatCount >= bus.getSeatCount()) {
            throw new AppException(ErrorCode.INVALID_KEY);
        }
        seatMapper.updateFromRequest(request, seat);
        seat.setBus(bus);
        seat.setUpdatedAt(LocalDateTime.now());
        return seatMapper.toResponse(seatRepository.save(seat));
    }

    @Override
    public void deleteSeat(Integer id) {
        if (!seatRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        seatRepository.deleteById(id);
    }

    @Override
    public SeatResponse getSeatById(Integer id) {
        return seatRepository.findById(id)
                .map(seatMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public List<SeatResponse> getSeatsByBusId(Integer busId) {
        return seatRepository.findByBusId(busId).stream()
                .map(seatMapper::toResponse)
                .collect(Collectors.toList());
    }
}