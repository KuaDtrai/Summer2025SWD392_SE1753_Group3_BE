package com.project.ibtss.mapper;

import com.project.ibtss.dto.request.StationRequest;
import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationMapper INSTANCE = Mappers.getMapper(StationMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Stations toEntity(StationRequest stationRequest);

    StationResponse toResponse(Stations station);
}