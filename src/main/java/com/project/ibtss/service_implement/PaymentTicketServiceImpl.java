package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.AdjusmentPaymentRequest;
import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.PaymentProcessResponse;
import com.project.ibtss.dto.response.PaymentResponse;
import com.project.ibtss.enums.*;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.*;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vn.payos.type.CheckoutResponseData;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final PaymentRepository paymentRepository;
    private final TicketPayRepository ticketPayRepository;
    private final TicketsRepository ticketsRepository;
    private final TicketSegmentRepository ticketSegmentRepository;
    private final SubPayRepository subPayRepository;
    private final SeatRepository seatRepository;
    private final RouteStationRepository routeStationRepository;
    private final EmailService emailService;

    @Transactional
    @Override
    public PaymentProcessResponse createPaymentTicket(PaymentSeatRequest request) throws Exception {
        Payment payment = paymentService.createPayment(request);

        List<Seats> seats = seatService.setStatusListSeat(request.getSeatIds());

        for (Seats seat : seats) {
            Tickets tickets = ticketService.createTicket(request);
            TicketSegment ticketSegment = ticketSegmentService.createTicketSegment(request, tickets, seat);
            TicketPay ticketPay = ticketPayService.createTicketPay(payment, tickets);
        }

        long orderCode = System.currentTimeMillis();

        // gen payos link
        CheckoutResponseData payosResponse = payOSService.createPaymentLink(request, orderCode, payment.getId(), PaymentType.PAY_TICKET.getDescription());

        return PaymentProcessResponse.builder()
                .paymentId(payment.getId())
                .price(request.getTotalPrice())
                .checkOutUrl(payosResponse.getCheckoutUrl())
                .returnUrl("http://localhost:5173/payment-callback")
                .cancelUrl("http://localhost:5173/payment-callback")
                .build();
    }

    @Transactional
    @Override
    public PaymentProcessResponse createAdjusmentPaymentTicket(AdjusmentPaymentRequest request) throws Exception {
        Payment payment = paymentRepository.findById(request.getPaymentId()).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        long orderCode = System.currentTimeMillis();

        CheckoutResponseData payosResponse = payOSService.createAdjusmentPaymentTicket(request, orderCode, payment.getId(), PaymentType.PAY_TICKET.getDescription());

        return PaymentProcessResponse.builder()
                .paymentId(payment.getId())
                .price(request.getTotalPrice())
                .checkOutUrl(payosResponse.getCheckoutUrl())
                .returnUrl("http://localhost:5173/payment-callback")
                .cancelUrl("http://localhost:5173/payment-callback")
                .build();
    }

    @Override
    public String confirmPaymentTicket(PaymentConfirmRequest request) throws MessagingException {
        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        String statusOfPayment = request.getStatus().toUpperCase();

        SubPay subPay = subPayRepository.findByPaymentId(payment.getId());

        if(subPay != null) {
            return processForAdjusmentPayment(subPay, statusOfPayment);
        }

        List<TicketPay> ticketPays = ticketPayRepository.findAllByPaymentId(payment.getId());
        List<Tickets> tickets = ticketPays.stream()
                .map(TicketPay::getTicket)
                .collect(Collectors.toList());



        if ("PAID".equals(statusOfPayment)) {
            updateTicketsAndSeats(tickets, TicketStatus.PAID, SeatStatus.BOOKED);
            payment.setUpdatedDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);

            emailService.sendEmail(tickets, "Đặt vé thành công", "");

            return "Success!";
        } else {
            updateTicketsAndSeats(tickets, TicketStatus.CANCELLED, SeatStatus.AVAILABLE);
            payment.setUpdatedDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.CANCELLED);
            paymentRepository.save(payment);

            return "Failed!";
        }
    }

    private String processForAdjusmentPayment(SubPay subPay, String statusPayment) throws MessagingException {

        TicketSegment ticketSegment = ticketSegmentRepository.findBySeatId(subPay.getNewSeat().getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));

        if ("PAID".equals(statusPayment)) {

            Seats newSeats = subPay.getNewSeat();
            newSeats.setSeatStatus(SeatStatus.BOOKED);
            seatRepository.save(newSeats);

            Payment payment = subPay.getPayment();
            payment.setUpdatedDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.PAID);

            paymentRepository.save(payment);

            //for send email
            Tickets ticket = ticketSegment.getTicket();
            List<Tickets> tickets = new ArrayList<>();
            tickets.add(ticket);

            emailService.sendTicketChangeSuccessEmail(ticket.getAccount().getEmail(), tickets);


            return "Success!";
        } else {

            Seats newSeats = subPay.getNewSeat();
            newSeats.setSeatStatus(SeatStatus.AVAILABLE);
            seatRepository.save(newSeats);

            Seats olSeats = subPay.getOldSeat();
            olSeats.setSeatStatus(SeatStatus.BOOKED);
            seatRepository.save(olSeats);

            ticketSegment.setSeat(olSeats);
            ticketSegmentRepository.save(ticketSegment);

            Payment payment = subPay.getPayment();
            payment.setUpdatedDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.CANCELLED);
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

    @Override
    public PaymentProcessResponse changeTicket(ChangeTicketRequest request) throws Exception {
        Tickets ticket = ticketsRepository.findById(request.getTicketId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));

        if(ticket.getStatus().equals(TicketStatus.USED) || ticket.getStatus().equals(TicketStatus.CANCELLED)){
            throw new AppException(ErrorCode.TICKET_IS_USED_OR_CANCELLED);
        }

        TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(request.getTicketId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));

        RouteStations fromRouteStations = routeStationRepository.findById(request.getFrom()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));
        RouteStations toRouteStations = routeStationRepository.findById(request.getTo()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));

        Seats newSeat = seatRepository.findById(request.getSeatId()).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));
        if(!newSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)){
            throw new AppException(ErrorCode.SEAT_NOT_AVAILABLE);
        }

        float priceDifference = request.getTotalPrice() - ticketSegment.getPrice();

        Seats prevSeat = ticketSegment.getSeat();
        prevSeat.setSeatStatus(SeatStatus.AVAILABLE);
        seatRepository.save(prevSeat);

        newSeat.setSeatStatus(SeatStatus.PENDING);
        ticketSegment.setSeat(newSeat);
        ticketSegment.setFromStation(fromRouteStations);
        ticketSegment.setToStation(toRouteStations);
        ticketSegment.setPrice(request.getTotalPrice());
        ticketSegmentRepository.save(ticketSegment);

        ticket.setBookingTime(LocalDateTime.now());
        ticketsRepository.save(ticket);

        if(priceDifference < 0){
            emailService.sendRefundNotificationEmail(ticket.getAccount().getEmail(), Math.abs(priceDifference));
            return null;
        } else if(priceDifference == 0){
            Tickets ticketChange = ticketSegment.getTicket();
            List<Tickets> tickets = new ArrayList<>();
            tickets.add(ticketChange);

            emailService.sendTicketChangeSuccessEmail( ticket.getAccount().getEmail(),tickets);
            return null;
        } else {
            Payment payment = paymentService.createPaymentForSubPay(priceDifference);
            SubPay subPay = SubPay.builder()
                    .type(PaymentType.ADJUSTMENT_PAY)
                    .amount(priceDifference)
                    .description(PaymentType.ADJUSTMENT_PAY.getDescription())
                    .payment(payment)
                    .oldSeat(prevSeat)
                    .newSeat(newSeat)
                    .build();
            subPayRepository.save(subPay);
            AdjusmentPaymentRequest adjusmentPaymentRequest = AdjusmentPaymentRequest.builder()
                    .paymentId(payment.getId())
                    .totalPrice(priceDifference)
                    .build();
            long orderCode = System.currentTimeMillis();

            CheckoutResponseData payosResponse = payOSService.createAdjusmentPaymentTicket(adjusmentPaymentRequest, orderCode, payment.getId(), PaymentType.PAY_TICKET.getDescription());

            return PaymentProcessResponse.builder()
                    .paymentId(payment.getId())
                    .price(request.getTotalPrice())
                    .checkOutUrl(payosResponse.getCheckoutUrl())
                    .returnUrl("http://localhost:5173/payment-callback")
                    .cancelUrl("http://localhost:5173/payment-callback")
                    .build();
        }
    }
}
