package com.project.ibtss.model;

import com.project.ibtss.utilities.enums.PaymentMethod;
import com.project.ibtss.utilities.enums.PaymentStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    Float totalAmount;

    @Enumerated(EnumType.STRING)
    PaymentStatus status;

    LocalDateTime createdDate;

    @Nullable
    LocalDateTime updatedDate;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    List<SubPay> subPays;

    @OneToMany(mappedBy = "payment", fetch = FetchType.LAZY)
    List<TicketPay> ticketPays;
}
