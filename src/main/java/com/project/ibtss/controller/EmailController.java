package com.project.ibtss.controller;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.enums.Gender;
import com.project.ibtss.enums.Position;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Customer;
import com.project.ibtss.model.Staff;
import com.project.ibtss.model.Tickets;
import com.project.ibtss.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @GetMapping()
    public ApiResponse<String> emailPage() throws MessagingException {
//        List<Tickets>  tickets = new ArrayList<>();
//        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Customer customer = new Customer(1, account, Gender.MALE, LocalDate.now(), "");
//        Staff  staff = new Staff(2, account, Position.SELLER, LocalDate.now());
//        Tickets ticket1 = new Tickets(1, "hehe123", customer, "kien", "0912345678", LocalDateTime.now(), "pending", staff);
//        Tickets ticket2 = new Tickets(2, "hehe123", customer, "khoa", "0912345678", LocalDateTime.now(), "pending", staff);
//        tickets.add(ticket1);
//        tickets.add(ticket2);
//        return emailService.sendEmail(tickets,"Tickets", "Tickets:");
        return null;
    }
}
