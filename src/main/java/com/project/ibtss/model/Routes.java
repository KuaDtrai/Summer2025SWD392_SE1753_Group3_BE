package com.project.ibtss.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne
    @JoinColumn(name = "departure_station_id")
    Stations departureStation;

    @ManyToOne
    @JoinColumn(name = "destination_station_id")
    Stations destinationStation;

    Integer distanceKm;
    LocalTime estimatedTime;
    Boolean isActive;
}
