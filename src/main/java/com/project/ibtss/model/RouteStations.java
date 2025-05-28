package com.project.ibtss.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteStations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Routes route;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Stations station;

    private Integer stationOrder;
}
