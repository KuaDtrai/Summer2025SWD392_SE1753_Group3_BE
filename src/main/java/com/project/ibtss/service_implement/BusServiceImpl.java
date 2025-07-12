package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.enums.BusStatus;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.BusMapper;
import com.project.ibtss.model.Buses;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.BusService;
import com.project.ibtss.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    private final SeatService seatService;
    private final TripRepository tripRepository;

    @Override
    public BusResponse createBus(BusRequest request) {

        checkValidCreateBus(request.getLicensePlate());

        Buses bus = busMapper.toEntity(request);
        bus.setStatus(BusStatus.ACTIVE);
        busRepository.save(bus);

        bus = busRepository.findByLicensePlateIgnoreCase(bus.getLicensePlate()).orElseThrow(() -> new AppException(ErrorCode.BUS_NOT_EXISTED));

        seatService.autoGenerateSeats(bus.getId());

        return busMapper.toResponse(bus);
    }

    private void checkValidCreateBus(String licensePlate) {
        if (busRepository.existsByLicensePlateIgnoreCase(licensePlate)) {
            throw new AppException(ErrorCode.BUS_EXISTED);
        }
    }

    @Override
    public List<BusResponse> getAllBuses() {
        return busRepository.findAll().stream()
                .map(busMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponse getBusById(Integer id) {
        return busRepository.findById(id)
                .map(busMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public BusResponse updateBus(Integer id, BusRequest request) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!bus.getLicensePlate().equalsIgnoreCase(request.getLicensePlate())
                && busRepository.existsByLicensePlateIgnoreCase(request.getLicensePlate())) {
            throw new AppException(ErrorCode.PHONE_EXISTED);
        }
        busMapper.updateFromRequest(request, bus);
        return busMapper.toResponse(busRepository.save(bus));
    }

    @Override
    public BusResponse setBusActive(Integer id) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        bus.setStatus(BusStatus.ACTIVE);
        return  busMapper.toResponse(busRepository.save(bus));
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