package com.project.ibtss.model;

import com.project.ibtss.enums.PaymentMethod;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    LocalDateTime createdDate;

    @Nullable
    LocalDateTime updatedDate;
}
