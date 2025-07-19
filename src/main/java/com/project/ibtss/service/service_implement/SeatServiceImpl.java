package com.project.ibtss.service.service_implement;

import com.project.ibtss.dto.request.SeatRequest;
import com.project.ibtss.dto.response.SeatForSelectResponse;
import com.project.ibtss.dto.response.SeatResponse;
import com.project.ibtss.utilities.enums.ErrorCode;
import com.project.ibtss.utilities.enums.SeatStatus;
import com.project.ibtss.utilities.exception.AppException;
import com.project.ibtss.utilities.mapper.SeatMapper;
import com.project.ibtss.model.Buses;
import com.project.ibtss.model.Seats;
import com.project.ibtss.model.Trips;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.repository.SeatRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.SeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private static final Logger log = LoggerFactory.getLogger(SeatServiceImpl.class);
    private final SeatRepository seatRepository;
    private final BusRepository busRepository;
    private final SeatMapper seatMapper;
    private final TripRepository tripRepository;

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
    @Transactional
    public List<SeatResponse> updateSeatsBySeatCount(Integer busId, int newSeatCount) {
        Buses bus = busRepository.findById(busId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        log.info("Updating seats for busId={}, newSeatCount={}", busId, newSeatCount);


        List<Seats> existingSeats = seatRepository.findByBusId(busId);

        int oldSeatCount = existingSeats.size();

        List<SeatResponse> result = new ArrayList<>();

        // if seat reduce -> delete biggest seat code
        if (newSeatCount < oldSeatCount) {
            // cal seat need delete
            int seatsToRemove = oldSeatCount - newSeatCount;

            // sort by seat code
            existingSeats.sort(Comparator.comparing(Seats::getSeatCode).reversed());

            for (int i = 0; i < seatsToRemove; i++) {
                Seats seatToDelete = existingSeats.get(i);
                seatRepository.delete(seatToDelete);
            }

            // get back after delete
            existingSeats = seatRepository.findByBusId(busId);
        }

        // if more -> create new
        if (newSeatCount > oldSeatCount) {
            int seatsToAdd = newSeatCount - oldSeatCount;

            // create seat from old seat count + 1
            int halfNewSeatCount = newSeatCount / 2;

            // Create seat start with A and B
            for (int i = 0; i < 2; i++) {
                for (int j = 1; j <= halfNewSeatCount; j++) {
                    String seatCode = (i == 0 ? "A" : "B") + j;

                    // skip if existed
                    boolean exists = existingSeats.stream()
                            .anyMatch(s -> s.getSeatCode().equalsIgnoreCase(seatCode));
                    if (exists) continue;

                    Seats seat = new Seats();
                    seat.setSeatCode(seatCode);
                    seat.setBus(bus);
                    seat.setCreatedAt(LocalDateTime.now());
                    seat.setUpdatedAt(LocalDateTime.now());
                    seat = seatRepository.save(seat);
                    seatRepository.flush();
                    existingSeats.add(seat);
                    log.info("Test create seat: id = {}", seat.getId());
                }
            }
        }

        // to response
        for (Seats seat : existingSeats) {
            result.add(seatMapper.toResponse(seat));
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

    @Override
    public List<SeatForSelectResponse> getAllSeatsForSelect(String licensePlate) {
        Buses bus = busRepository.findByLicensePlateIgnoreCase(licensePlate).orElseThrow(() -> new AppException(ErrorCode.BUS_NOT_EXISTED));
        List<Seats> seats = seatRepository.findAllSeatByBusId(bus.getId());
        return seats.stream()
                .map(this::toForSelectResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatForSelectResponse> getListSeatByTrip(Integer tripId) {
        Trips trip = tripRepository.findById(tripId).orElseThrow(() -> new AppException(ErrorCode.TRIP_NOT_EXISTED));
        List<Seats> seats = seatRepository.findByBusId(trip.getBus().getId());
        return seats.stream()
                .map(this::toForSelectResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Seats> setStatusListSeat(List<Integer> seatIds) {
        List<Seats> result = new ArrayList<>();
        for (Integer seatId : seatIds) {
            result.add(seatRepository.save(setStatusSeat(seatId)));
        }
        return result;
    }

    private Seats setStatusSeat(int seatId){
        Seats seat = seatRepository.findById(seatId).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));
        seat.setSeatStatus(SeatStatus.PENDING);
        return seatRepository.save(seat);
    }

    private SeatForSelectResponse toForSelectResponse(Seats seat) {
        return SeatForSelectResponse.builder()
                .seatId(seat.getId())
                .busId(seat.getBus().getId())
                .seatCode(seat.getSeatCode())
                .seatStatus(seat.getSeatStatus())
                .build();
    }
}