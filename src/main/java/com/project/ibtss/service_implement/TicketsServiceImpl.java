package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.TicketStatus;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Customer;
import com.project.ibtss.model.Tickets;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.CustomerRepository;
import com.project.ibtss.repository.TicketsRepository;
import com.project.ibtss.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketsServiceImpl implements TicketService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TicketsRepository ticketsRepository;

    @Override
    public Tickets createTicket(PaymentSeatRequest request) {
        Account account = accountRepository.findByPhone(request.getPhone());
        Customer customer = customerRepository.findByAccountId(account.getId()).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_EXISTED));
        Tickets tickets = Tickets.builder()
                .journeyCode(String.valueOf(System.currentTimeMillis()))
                .customer(customer)
                .ticketOwnerName(request.getFullName())
                .ticketOwnerPhone(request.getPhone())
                .bookingTime(LocalDateTime.now())
                .status(TicketStatus.PENDING)
                .build();
        return ticketsRepository.save(tickets);
    }
}
