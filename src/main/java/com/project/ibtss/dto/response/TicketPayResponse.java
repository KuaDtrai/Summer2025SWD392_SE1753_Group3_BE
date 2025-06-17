package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class TicketPayResponse {
    private Integer id;
    private PaymentResponse payment;
    private String journeyCode;
    private Float amount;
}
