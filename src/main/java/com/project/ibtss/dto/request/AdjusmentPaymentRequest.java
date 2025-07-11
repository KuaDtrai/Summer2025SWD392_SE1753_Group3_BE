package com.project.ibtss.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdjusmentPaymentRequest {
    int paymentId;
    Float totalPrice;
}
