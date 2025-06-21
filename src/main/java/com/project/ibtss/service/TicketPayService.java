package com.project.ibtss.service;

import com.project.ibtss.model.Payment;
import com.project.ibtss.model.TicketPay;
import com.project.ibtss.model.Tickets;

public interface TicketPayService {
    TicketPay createTicketPay(Payment payment, Tickets tickets);
}
