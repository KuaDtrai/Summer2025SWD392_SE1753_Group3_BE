package com.project.ibtss.service.service_implement;

import com.project.ibtss.model.Payment;
import com.project.ibtss.model.TicketPay;
import com.project.ibtss.model.Tickets;
import com.project.ibtss.repository.TicketPayRepository;
import com.project.ibtss.service.TicketPayService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketPayServiceImpl implements TicketPayService {
    private final TicketPayRepository ticketPayRepository;

    @Transactional
    @Override
    public TicketPay createTicketPay(Payment payment, Tickets tickets) {
        TicketPay ticketPay = TicketPay.builder()
                .payment(payment)
                .amount(payment.getTotalAmount())
                .ticket(tickets)
                .build();
        return ticketPayRepository.save(ticketPay);
    }
}
