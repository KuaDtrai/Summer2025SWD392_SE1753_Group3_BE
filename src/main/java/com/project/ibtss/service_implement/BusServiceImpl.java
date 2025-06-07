package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.mapper.BusMapper;
import com.project.ibtss.model.Buses;
import com.project.ibtss.repository.BusRepository;
import com.project.ibtss.service.BusService;
import com.project.ibtss.enums.BusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusMapper busMapper;

    @Override
    public BusResponse createBus(BusRequest busRequest) {
        validateBusRequest(busRequest);

        if (busRepository.existsByLicensePlate(busRequest.getLicensePlate())) {
            throw new IllegalArgumentException("Bus with license plate " + busRequest.getLicensePlate() + " already exists.");
        }

        Buses bus = busMapper.toEntity(busRequest);
        Buses savedBus = busRepository.save(bus);
        return busMapper.toResponse(savedBus);
    }

    @Override
    public BusResponse getBusById(Integer id) {
        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + id));
        return busMapper.toResponse(bus);
    }

    @Override
    public List<BusResponse> getAllBuses() {
        return busRepository.findAll().stream()
                .map(busMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BusResponse updateBus(Integer id, BusRequest busRequest) {
        validateBusRequest(busRequest);

        Buses bus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with ID: " + id));

        if (busRepository.existsByLicensePlateAndIdNot(busRequest.getLicensePlate(), id)) {
            throw new IllegalArgumentException("Another bus with license plate " + busRequest.getLicensePlate() + " already exists.");
        }

        Buses updatedBus = busMapper.toEntity(busRequest);
        updatedBus.setId(id); // Giữ ID của bản ghi cũ
        updatedBus = busRepository.save(updatedBus);
        return busMapper.toResponse(updatedBus);
    }

    @Override
    public void deleteBus(Integer id) {
        if (!busRepository.existsById(id)) {
            throw new IllegalArgumentException("Bus not found with ID: " + id);
        }
        busRepository.deleteById(id);
    }

    private void validateBusRequest(BusRequest busRequest) {
        if (busRequest.getLicensePlate() == null || busRequest.getLicensePlate().trim().isEmpty()) {
            throw new IllegalArgumentException("License plate is required.");
        }
        if (busRequest.getSeatCount() == null || busRequest.getSeatCount() <= 0) {
            throw new IllegalArgumentException("Seat count must be greater than 0.");
        }
        if (busRequest.getBusType() == null || busRequest.getBusType().trim().isEmpty()) {
            throw new IllegalArgumentException("Bus type is required.");
        }
        try {
            BusType.valueOf(busRequest.getBusType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid bus type. Must be one of: " +
                    String.join(", ", Arrays.stream(BusType.values()).map(Enum::toString).toList()));
        }
    }
}