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
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String journeyCode;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    Customer customer;

    String ticketOwnerName;
    String ticketOwnerPhone;
    LocalDateTime bookingTime;
    String status;

    @ManyToOne
    @JoinColumn(name = "sold_by")
    Staff soldBy;
}
