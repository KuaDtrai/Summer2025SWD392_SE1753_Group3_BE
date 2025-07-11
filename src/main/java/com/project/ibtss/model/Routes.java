package com.project.ibtss.model;

import com.project.ibtss.enums.RouteStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(length = 50)
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_station_id")
    Stations departureStation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_station_id")
    Stations destinationStation;

    Integer distanceKm;
    LocalTime estimatedTime;

    @Enumerated(EnumType.STRING)
    RouteStatus status;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    List<Trips> trips;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    List<RouteStations> routeStations;
}
