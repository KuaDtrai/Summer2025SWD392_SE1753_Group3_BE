package com.project.ibtss.service.service_implement;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.utilities.enums.ErrorCode;
import com.project.ibtss.utilities.exception.AppException;
import com.project.ibtss.utilities.mapper.TicketMapper;
import com.project.ibtss.model.Account;
import com.project.ibtss.model.Tickets;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final AccountRepository accountRepository;
    private final JavaMailSender mailSender;
    private final TicketMapper ticketMapper;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

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
        helper.setFrom(fromEmail);

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

    @Override
    public ApiResponse<String> sendVerificationEmail(String email, String verificationCode) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Xác thực email của bạn");
        helper.setFrom(fromEmail);

        // Tạo liên kết xác thực
        String verificationLink = "http://localhost:8080/api/auth/verify?code=" + verificationCode;

        // Chuẩn bị context cho Thymeleaf
        Context context = new Context();
        context.setVariable("verificationLink", verificationLink);
        context.setVariable("code", verificationCode);

        // Render template HTML
        String htmlContent = templateEngine.process("verification-email", context);
        helper.setText(htmlContent, true);

        // Gửi email
        mailSender.send(mimeMessage);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Verification email sent successfully")
                .build();
    }

    @Override
    public ApiResponse<String> sendRefundNotificationEmail(String email, float refundAmount) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Thông báo hoàn tiền chênh lệch");

        helper.setFrom(fromEmail);

        // Chuẩn bị nội dung email
        Context context = new Context();
        context.setVariable("refundAmount", refundAmount);
        context.setVariable("message", "Bạn đã đổi sang một vé có giá thấp hơn. Chúng tôi sẽ liên hệ bạn để hoàn tiền chênh lệch trong thời gian sớm nhất.");

        // Render template
        String htmlContent = templateEngine.process("refund-notification-email", context);

        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);

        return ApiResponse.<String>builder()
                .code(200)
                .message("Refund notification email sent successfully")
                .build();
    }

    @Override
    public ApiResponse<String> sendTicketChangeSuccessEmail(String email, List<Tickets> newTickets) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Thông báo đổi vé thành công");
        helper.setFrom(fromEmail);

        // Map Tickets → TicketResponse
        List<TicketResponse> ticketResponses = newTickets.stream()
                .map(ticketMapper::toTicketResponse)
                .collect(Collectors.toList());

        Context context = new Context();
        context.setVariable("tickets", ticketResponses);

        String htmlContent = templateEngine.process("template", context);

        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);

        return ApiResponse.<String>builder()
                .code(200)
                .message("Ticket change success email sent successfully")
                .build();
    }

    @Override
    public void sendFeedbackReplyEmail(String email, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Phản hồi góp ý của bạn");
        helper.setFrom(fromEmail);

        // Thiết lập biến cho template
        Context context = new Context();
        context.setVariable("content", content);

        // Xử lý template
        String htmlContent = templateEngine.process("feedback-reply-template", context);

        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }
}