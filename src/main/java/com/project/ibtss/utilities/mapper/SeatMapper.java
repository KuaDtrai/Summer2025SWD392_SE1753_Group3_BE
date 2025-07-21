package com.project.ibtss.utilities.mapper;

import com.project.ibtss.dto.request.SeatRequest;
import com.project.ibtss.dto.response.SeatForSelectResponse;
import com.project.ibtss.dto.response.SeatResponse;
import com.project.ibtss.model.Seats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(source = "bus.id", target = "busId")
    @Mapping(source = "bus.licensePlate", target = "busPlate")
    SeatResponse toResponse(Seats seat);
    Seats toEntity(SeatRequest request);
    void updateFromRequest(SeatRequest request, @MappingTarget Seats seat);
    SeatForSelectResponse toForSelectResponse(Seats seat);
}