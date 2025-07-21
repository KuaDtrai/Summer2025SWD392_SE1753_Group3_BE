package com.project.ibtss.controller;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
