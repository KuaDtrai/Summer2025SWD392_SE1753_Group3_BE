package com.project.ibtss.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    Routes route;

    LocalDateTime departureTime;
    LocalDateTime arrivalTime;

    Float price;

    @ManyToOne
    @JoinColumn(name = "bus_id")
    Buses bus;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    Account driver;

    String status;
}
