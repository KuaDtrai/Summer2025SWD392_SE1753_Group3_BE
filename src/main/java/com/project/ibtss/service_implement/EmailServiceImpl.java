package com.project.ibtss.service_implement;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.mapper.TicketMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Tickets;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final AccountRepository accountRepository;
    private final JavaMailSender mailSender;
    private final TicketMapper ticketMapper;
    private final SpringTemplateEngine templateEngine;

    @Override
    public ApiResponse<String> sendEmail(List<Tickets> tickets, String subject, String body) throws MessagingException{
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        account = accountRepository.findById(account.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (account.getEmail() == null || account.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        List<TicketResponse> ticketResponseList = new ArrayList<>();
        for (Tickets ticket : tickets) {
            ticketResponseList.add(ticketMapper.toTicketResponse(ticket));
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // Set email properties
        helper.setTo(account.getEmail());
        helper.setSubject(subject);
        helper.setFrom("your-email@domain.com"); // Replace with your sender email

        // Prepare Thymeleaf context
        Context context = new Context();
        context.setVariable("tickets", ticketResponseList);

        // Process the HTML template
        String htmlContent = templateEngine.process("template", context);

        // Set the email content as HTML
        helper.setText(htmlContent, true);

        // Send the email
        mailSender.send(mimeMessage);
        return null;
    }
}