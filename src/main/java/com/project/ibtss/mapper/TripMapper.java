package com.project.ibtss.mapper;

import com.project.ibtss.dto.request.TripRequest;
import com.project.ibtss.dto.response.TripResponse;
import com.project.ibtss.model.Trips;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TripMapper {
    @Mapping(source = "route.name", target = "routeName")
    @Mapping(source = "bus.licensePlate", target = "busPlate")
    @Mapping(source = "driver.fullName", target = "driverName")
    TripResponse toResponse(Trips trip);
    Trips toEntity(TripRequest request);
    void updateFromRequest(TripRequest request, @MappingTarget Trips trip);
}