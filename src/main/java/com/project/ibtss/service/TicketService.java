package com.project.ibtss.service;

import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.model.Tickets;

import java.util.List;

public interface TicketService {
    Tickets createTicket(PaymentSeatRequest request);
    List<TicketResponse> getAllTicketByAccountId(int accountId);
    TicketResponse searchTicketByCode(String code);
    TicketResponse searchTicketById(Integer ticketId);

    String cancelTicket(Integer ticketId);
}
