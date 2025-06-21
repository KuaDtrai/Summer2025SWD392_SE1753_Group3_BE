package com.project.ibtss.dto.request;

import com.project.ibtss.enums.PayOSPaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentConfirmRequest {
    int paymentId;
    PayOSPaymentStatus status;
    Float amount;
}
