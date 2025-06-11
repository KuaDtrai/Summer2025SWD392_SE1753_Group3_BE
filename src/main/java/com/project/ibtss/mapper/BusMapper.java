package com.project.ibtss.mapper;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.model.Buses;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BusMapper {
    BusMapper INSTANCE = Mappers.getMapper(BusMapper.class);

    @Mapping(target = "busType", expression = "java(busRequest.getBusType().toUpperCase())")
    Buses toEntity(BusRequest busRequest);

    @Mapping(target = "id", source = "id") // Đảm bảo ánh xạ id
    @Mapping(target = "licensePlate", source = "licensePlate")
    @Mapping(target = "seatCount", source = "seatCount")
    @Mapping(target = "busType", source = "busType")
    BusResponse toResponse(Buses bus);
}