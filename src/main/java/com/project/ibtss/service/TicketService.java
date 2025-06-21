package com.project.ibtss.service;

import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.model.Tickets;

public interface TicketService {
    Tickets createTicket(PaymentSeatRequest request);
}
