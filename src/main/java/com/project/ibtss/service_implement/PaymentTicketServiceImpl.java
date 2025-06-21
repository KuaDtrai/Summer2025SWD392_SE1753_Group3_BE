package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.CreatePaymentResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.PaymentMethod;
import com.project.ibtss.enums.SeatStatus;
import com.project.ibtss.enums.TicketStatus;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.payos.type.CheckoutResponseData;

import java.time.LocalDateTime;
import java.util.List;

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


    @Transactional
    @Override
    public CreatePaymentResponse createPaymentTicket(PaymentSeatRequest request) {
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
                .returnUrl("http://localhost:5173/")
                .cancelUrl("http://localhost:5173/")
                .build();
    }

    @Override
    public CreatePaymentResponse confirmPaymentTicket(PaymentConfirmRequest request) {
        Payment payment = paymentRepository.findById(request.getPaymentId()).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        return null;
    }

    private void updateStatusSeat(List<Seats> seats){

    }
}
