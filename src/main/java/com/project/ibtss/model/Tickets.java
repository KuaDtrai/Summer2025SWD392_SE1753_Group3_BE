package com.project.ibtss.model;

import com.project.ibtss.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String journeyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    Customer customer;

    String ticketOwnerName;
    String ticketOwnerPhone;
    LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    TicketStatus status;
}
