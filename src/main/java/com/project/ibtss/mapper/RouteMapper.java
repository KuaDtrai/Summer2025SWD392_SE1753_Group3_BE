package com.project.ibtss.mapper;

import com.project.ibtss.dto.response.RouteResponse;
import com.project.ibtss.model.Routes;
import org.mapstruct.Mapper;
import org.springframework.util.RouteMatcher;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteResponse toRouteResponse(Routes route);
}
