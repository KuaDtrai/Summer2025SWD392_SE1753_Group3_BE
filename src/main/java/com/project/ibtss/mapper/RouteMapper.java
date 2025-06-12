package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.model.Routes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.util.RouteMatcher;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    @Mapping(target = "distanceKm", source = "distanceKm")
    RouteResponse toRouteResponse(Routes route);
}
