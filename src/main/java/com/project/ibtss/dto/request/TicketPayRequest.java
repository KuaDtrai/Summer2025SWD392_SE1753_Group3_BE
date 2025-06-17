package com.project.ibtss.dto.request;

import lombok.Getter;

@Getter
public class TicketPayRequest {
    private Integer paymentId;
    private Integer ticketId;
    private Float amount;
}
