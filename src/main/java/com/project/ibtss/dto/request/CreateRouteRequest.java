package com.project.ibtss.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class CreateRouteRequest {
    @NotBlank
    private String name;

    @NotNull
    private Integer departureStationId;

    @NotNull
    private Integer destinationStationId;

    private List<@NotNull Integer> listStationId;

    @NotNull
    @Min(50)
    private Integer distanceKm;

    @NotNull
    private LocalTime estimatedTime;
}
