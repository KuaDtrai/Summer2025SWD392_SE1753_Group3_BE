package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.CreatePaymentResponse;
import com.project.ibtss.enums.*;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.payos.type.CheckoutResponseData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTicketServiceImpl implements PaymentTicketService {
    private final PaymentService paymentService;
    private final TicketService ticketService;
    private final TicketPayService ticketPayService;
    private final SeatService seatService;
    private final PayOSService payOSService;
    private final TicketSegmentService ticketSegmentService;
    private final TripRepository tripRepository;
    private final PaymentRepository paymentRepository;
    private final TicketPayRepository ticketPayRepository;
    private final TicketsRepository ticketsRepository;
    private final TicketSegmentRepository ticketSegmentRepository;

    @Transactional
    @Override
    public CreatePaymentResponse createPaymentTicket(PaymentSeatRequest request) throws Exception {
        Payment payment = paymentService.createPayment(request);

        List<Seats> seats = seatService.setStatusListSeat(request.getSeatIds());

        for (Seats seat : seats) {
            Tickets tickets = ticketService.createTicket(request);
            TicketSegment ticketSegment = ticketSegmentService.createTicketSegment(request, tickets, seat);
            TicketPay ticketPay = ticketPayService.createTicketPay(payment, tickets);
        }

        long orderCode = System.currentTimeMillis();

        // gen payos link
        CheckoutResponseData payosResponse = payOSService.createPaymentLink(request, orderCode, payment.getId());

        return CreatePaymentResponse.builder()
                .paymentId(payment.getId())
                .price(request.getTotalPrice())
                .checkOutUrl(payosResponse.getCheckoutUrl())
                .returnUrl("http://localhost:5173/payment-callback")
                .cancelUrl("http://localhost:5173/payment-callback")
                .build();
    }

    @Override
    public String confirmPaymentTicket(PaymentConfirmRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        List<TicketPay> ticketPays = ticketPayRepository.findAllByPaymentId(payment.getId());
        List<Tickets> tickets = ticketPays.stream()
                .map(TicketPay::getTicket)
                .collect(Collectors.toList());

        String status = request.getStatus().toUpperCase();

        if ("PAID".equals(status)) {
            updateTicketsAndSeats(tickets, TicketStatus.PAID, SeatStatus.BOOKED);
            payment.setUpdatedDate(LocalDateTime.now());
            paymentRepository.save(payment);

            return "Success!";
        } else {
            updateTicketsAndSeats(tickets, TicketStatus.CANCEL, SeatStatus.AVAILABLE);
            payment.setUpdatedDate(LocalDateTime.now());
            paymentRepository.save(payment);

            return "Failed!";
        }
    }

    private void updateTicketsAndSeats(List<Tickets> tickets, TicketStatus ticketStatus, SeatStatus seatStatus) {
        for (Tickets ticket : tickets) {
            ticket.setStatus(ticketStatus);
            ticketsRepository.save(ticket);

            TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId())
                    .orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));

            ticketSegment.getSeat().setSeatStatus(seatStatus);
            ticketSegmentRepository.save(ticketSegment);
        }
    }
}
