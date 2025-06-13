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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;
    private final SeatMapper seatMapper;

    @Override
    public SeatResponse createSeat(SeatRequest request) {
        Seats seat = seatMapper.toEntity(request);
        Buses bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        seat.setBus(bus);
        return seatMapper.toResponse(seatRepository.save(seat));
    }

    @Override
    public SeatResponse updateSeat(Integer id, SeatRequest request) {
        Seats seat = seatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        seatMapper.updateFromRequest(request, seat);
        Buses bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        seat.setBus(bus);
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