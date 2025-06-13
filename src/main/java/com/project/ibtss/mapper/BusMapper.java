package com.project.ibtss.mapper;

import com.project.ibtss.dto.request.BusRequest;
import com.project.ibtss.dto.response.BusResponse;
import com.project.ibtss.model.Buses;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BusMapper {
    BusResponse toResponse(Buses bus);

    Buses toEntity(BusRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Ignore null properties during update
    void updateFromRequest(BusRequest request, @MappingTarget Buses bus);
}
