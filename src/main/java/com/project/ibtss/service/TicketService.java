package com.project.ibtss.service;

import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.PaymentProcessResponse;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.model.Tickets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {
    Tickets createTicket(PaymentSeatRequest request);
    Page<TicketResponse> getAllTicketByAccountId(int accountId, int page, int size);
    TicketResponse searchTicketByCode(String code);
    TicketResponse searchTicketById(Integer ticketId);
//    PaymentProcessResponse changeTicket(ChangeTicketRequest request) throws Exception;
    String cancelTicket(Integer ticketId);
}
