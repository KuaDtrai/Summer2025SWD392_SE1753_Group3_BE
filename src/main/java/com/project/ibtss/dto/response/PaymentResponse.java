package com.project.ibtss.dto.response;

import com.project.ibtss.utilities.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private Integer id;
    private PaymentMethod paymentMethod;
    private Float totalAmount;
    private LocalDateTime createdDate;
}
