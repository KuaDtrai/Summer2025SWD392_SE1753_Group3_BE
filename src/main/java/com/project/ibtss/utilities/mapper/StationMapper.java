package com.project.ibtss.utilities.mapper;

import com.project.ibtss.dto.response.StationResponse;
import com.project.ibtss.model.Stations;
import org.mapstruct.Mapper;
import org.springframework.util.RouteMatcher;

@Mapper(componentModel = "spring")
public interface StationMapper {
    StationResponse toStationResponse(Stations stations);
}
