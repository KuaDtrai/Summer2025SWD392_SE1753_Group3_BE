package com.project.ibtss.mapper;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.model.Buses;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BusMapper {
    BusResponse toResponse(Buses bus);
    Buses toEntity(BusRequest request);
    Buses updateFromRequest(BusRequest request, @MappingTarget Buses bus);
}
