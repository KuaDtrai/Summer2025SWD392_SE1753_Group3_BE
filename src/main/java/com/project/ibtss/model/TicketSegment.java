package com.project.ibtss.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "journey_ticket_id")
    Tickets ticket;

    Integer legOrder;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    Trips trip;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    Seats seat;

    @ManyToOne
    @JoinColumn(name = "from_station_id")
    RouteStations fromStation;

    @ManyToOne
    @JoinColumn(name = "to_station_id")
    RouteStations toStation;

    Float price;
}
