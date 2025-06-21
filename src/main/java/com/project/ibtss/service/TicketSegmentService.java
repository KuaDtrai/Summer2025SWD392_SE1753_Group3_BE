package com.project.ibtss.service;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.model.Seats;
import com.project.ibtss.model.TicketSegment;
import com.project.ibtss.model.Tickets;

public interface TicketSegmentService {
    TicketSegment createTicketSegment(PaymentSeatRequest request, Tickets ticket, Seats seat);
}
