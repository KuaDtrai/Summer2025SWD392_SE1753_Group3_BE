package com.project.ibtss.model;

import com.project.ibtss.utilities.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    Payment payment;

    PaymentType type;
    Float amount;
    @Column(length = 50)
    String description;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_seat_id")
    Seats oldSeat;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "new_seat_id")
    Seats newSeat;
}
