package com.project.ibtss.dto.request;

import com.project.ibtss.enums.PaymentMethod;
import lombok.Getter;

@Getter
public class PaymentRequest {
    private PaymentMethod paymentMethod;

}
