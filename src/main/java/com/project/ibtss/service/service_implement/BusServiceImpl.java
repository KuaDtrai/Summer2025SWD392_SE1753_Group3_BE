package com.project.ibtss.service.service_implement;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.utilities.enums.BusStatus;
import com.project.ibtss.utilities.enums.ErrorCode;
import com.project.ibtss.utilities.enums.TicketStatus;
import com.project.ibtss.utilities.enums.TripsStatus;
import com.project.ibtss.utilities.exception.AppException;
import com.project.ibtss.utilities.mapper.BusMapper;
import com.project.ibtss.model.Buses;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.repository.TicketSegmentRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.BusService;
import com.project.ibtss.service.SeatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private static final Logger log = LoggerFactory.getLogger(BusServiceImpl.class);
    private final BusRepository busRepository;
    private final BusMapper busMapper;

    private final SeatService seatService;
    private final TripRepository tripRepository;
    private final TicketSegmentRepository ticketSegmentRepository;

    @Override
    @Transactional
    public BusResponse createBus(BusRequest request) {

        checkValidCreateBus(request.getLicensePlate());

        log.info(request.getSeatCount().toString());

        Buses bus = busMapper.toEntity(request);
        bus.setStatus(BusStatus.ACTIVE);
        bus = busRepository.save(bus);

        seatService.updateSeatsBySeatCount(bus.getId(), bus.getSeatCount());

        return busMapper.toResponse(bus);
    }

    private void checkValidCreateBus(String licensePlate) {
        if (busRepository.existsByLicensePlateIgnoreCase(licensePlate)) {
            throw new AppException(ErrorCode.BUS_EXISTED);
        }
    }

    @Override
    public Page<BusResponse> getAllBuses(Pageable pageable) {
        return busRepository.findAll(pageable)
                .map(busMapper::toResponse);
    }

    @Override
    public BusResponse getBusById(Integer id) {
        return busRepository.findById(id)
                .map(busMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.BUS_NOT_EXISTED));
    }

    @Override
    @Transactional
    public BusResponse updateBus(Integer id, BusRequest request) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUS_NOT_EXISTED));

        if(checkTickets(id)){
            throw new AppException(ErrorCode.CANT_EDIT_BUS);
        }

        if (!bus.getLicensePlate().equalsIgnoreCase(request.getLicensePlate())
                && busRepository.existsByLicensePlateIgnoreCase(request.getLicensePlate())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }

        if(bus.getSeatCount() > 0){
            seatService.updateSeatsBySeatCount(bus.getId(), bus.getSeatCount());
        }

        bus = busMapper.updateFromRequest(request, bus);
        bus = busRepository.save(bus);
        return busMapper.toResponse(busRepository.save(bus));
    }

    private boolean checkTickets(Integer busId) {
        List<String> usedStatuses = List.of(TicketStatus.USED.getName(), TicketStatus.PAID.getName(), TicketStatus.PENDING.getName(), TicketStatus.CANCELLED.getName());
        long count = ticketSegmentRepository.countBySeat_Bus_IdAndTicket_StatusIn(busId, usedStatuses);
        return count > 0;
    }

    @Override
    public BusResponse setBusStatus(Integer id, String status) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(checkTickets(id)){
            throw new AppException(ErrorCode.CANT_EDIT_BUS);
        }
        BusStatus newStatus = BusStatus.ACTIVE; //default value to avoid null
        if(status != null){
            newStatus = BusStatus.valueOf(status);
        }

        if(newStatus.equals(BusStatus.INACTIVE) || newStatus == BusStatus.MAINTENANCE){
            List<TripsStatus> activeTripStatuses = List.of(
                    TripsStatus.SCHEDULED,
                    TripsStatus.IN_PROGRESS,
                    TripsStatus.DELAYED
            );

            boolean isAssigned = tripRepository.existsByBus_IdAndStatusIn(bus.getId(), activeTripStatuses);

            if (isAssigned) {
                throw new AppException(ErrorCode.BUS_STILL_ASSIGNED_TO_ACTIVE_TRIP);
            }
        }
        bus.setStatus(newStatus);
        bus = busRepository.save(bus);
        return  busMapper.toResponse(bus);
    }

    @Override
    public List<BusResponse> searchByLicensePlate(String keyword) {
        return busRepository.findByLicensePlateContainingIgnoreCase(keyword).stream()
                .map(busMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BusResponse> searchBuses(int page, String search) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        Page<Buses> busesPage = busRepository.findByLicensePlateContainingIgnoreCase(search, pageable);
        return busesPage.map(busMapper::toResponse);
    }

    @Override
    public Page<BusResponse> getAvailableBuses(LocalDateTime departureTime, LocalDateTime arrivalTime, Pageable pageable, String search) {
        Pageable correctedPageable = PageRequest.of(Math.max(pageable.getPageNumber() - 1, 0), pageable.getPageSize());

        List<BusResponse> availableBuses = busRepository.findByStatus(BusStatus.ACTIVE).stream()
                .filter(bus -> bus.getLicensePlate().toLowerCase().contains(search.toLowerCase()) || bus.getBusType().name().toLowerCase().contains(search.toLowerCase()))
                .filter(bus -> !tripRepository.existsByBus_IdAndDepartureTimeLessThanAndArrivalTimeGreaterThan(
                        bus.getId(), arrivalTime, departureTime))
                .map(busMapper::toResponse)
                .toList();

        return new PageImpl<>(
                availableBuses.stream()
                        .skip(correctedPageable.getOffset())
                        .limit(correctedPageable.getPageSize())
                        .toList(),
                correctedPageable,
                availableBuses.size()
        );
    }
}