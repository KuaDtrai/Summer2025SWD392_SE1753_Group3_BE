package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.BusMapper;
import com.project.ibtss.model.Buses;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.service.BusService;
import com.project.ibtss.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;
    private final SeatService seatService;

    @Override
    public BusResponse createBus(BusRequest request) {
        if (busRepository.existsByLicensePlateIgnoreCase(request.getLicensePlate())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        Buses bus = busMapper.toEntity(request);
        bus.setStatus("ACTIVE");
        bus = busRepository.save(bus); // Save trước để lấy ID

        seatService.autoGenerateSeats(bus.getId()); // Tự động sinh ghế

        return busMapper.toResponse(bus);
    }

    @Override
    public List<BusResponse> getAllBuses() {
        return busRepository.findAll().stream()
                .filter(bus -> "ACTIVE".equals(bus.getStatus()))
                .map(busMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponse getBusById(Integer id) {
        return busRepository.findById(id)
                .filter(bus -> "ACTIVE".equals(bus.getStatus()))
                .map(busMapper::toResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public BusResponse updateBus(Integer id, BusRequest request) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!bus.getLicensePlate().equalsIgnoreCase(request.getLicensePlate())
                && busRepository.existsByLicensePlateIgnoreCase(request.getLicensePlate())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        busMapper.updateFromRequest(request, bus);
        return busMapper.toResponse(busRepository.save(bus));
    }

    @Override
    public void deleteBus(Integer id) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        bus.setStatus("INACTIVE"); // Xoá mềm
        busRepository.save(bus);
    }

    @Override
    public List<BusResponse> searchByLicensePlate(String keyword) {
        return busRepository.findByLicensePlateContainingIgnoreCase(keyword).stream()
                .filter(bus -> "ACTIVE".equals(bus.getStatus()))
                .map(busMapper::toResponse)
                .collect(Collectors.toList());
    }
}
