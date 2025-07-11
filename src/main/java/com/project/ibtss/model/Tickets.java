package com.project.ibtss.model;

import com.project.ibtss.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "account_id", nullable = true)
    Account account;

    String ticketOwnerName;
    String ticketOwnerPhone;
    LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    TicketStatus status;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    List<TicketSegment> segments;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    List<TicketPay> ticketPays;
}
