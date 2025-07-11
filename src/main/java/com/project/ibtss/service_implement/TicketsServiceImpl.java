package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.AdjusmentPaymentRequest;
import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.PaymentProcessResponse;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.dto.response.TicketSegmentResponse;
import com.project.ibtss.enums.*;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.PaymentService;
import com.project.ibtss.service.PaymentTicketService;
import com.project.ibtss.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketsServiceImpl implements TicketService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TicketsRepository ticketsRepository;
    private final TripRepository tripRepository;
    private final TicketSegmentRepository ticketSegmentRepository;
    private final TicketPayRepository ticketPayRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final RouteStationRepository routeStationRepository;
    private final SeatRepository seatRepository;
    private final PaymentService paymentService;
    private final SubPayRepository subPayRepository;

    private Account getAccountContext() {
        return accountRepository.findByPhone(SecurityContextHolder.getContext().getAuthentication().getName());
    }


    @Override
    public Tickets createTicket(PaymentSeatRequest request) {
        Account account = accountRepository.findByPhone(request.getPhone());
        Tickets tickets = Tickets.builder()
                .journeyCode(String.valueOf(System.currentTimeMillis()))
                .account(account)
                .ticketOwnerName(request.getFullName())
                .ticketOwnerPhone(request.getPhone())
                .bookingTime(LocalDateTime.now())
                .status(TicketStatus.PENDING)
                .build();
        return ticketsRepository.save(tickets);
    }

    @Override
    public Page<TicketResponse> getAllTicketByAccountId(int accountId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookingTime").descending());
        Page<Tickets> ticketsPage = ticketsRepository.findAllByAccountId(accountId, pageable);

        List<TicketResponse> responses = ticketsPage.stream().map(ticket -> {
            // Lấy TicketSegment
            TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));

            // Lấy thanh toán
            TicketPay ticketPay = ticketPayRepository.findByTicketId(ticket.getId());
            Float amount = ticketSegment.getPrice();

            // Dựng response segment
            TicketSegmentResponse segmentResponse = toTicketSegmentResponse(
                    ticketSegment,
                    amount,
                    ticketSegment.getTrip().getDepartureTime()
            );

            // Build TicketResponse
            return TicketResponse.builder()
                    .ticketId(ticket.getId())
                    .journeyCode(ticket.getJourneyCode())
                    .ticketOwnerName(ticket.getAccount().getFullName())
                    .ticketOwnerPhone(ticket.getAccount().getPhone())
                    .bookingTime(ticket.getBookingTime())
                    .status(ticket.getStatus().getName())
                    .ticketSegmentResponse(segmentResponse)
                    .build();

        }).toList();

        return new PageImpl<>(responses, pageable, ticketsPage.getTotalElements());
    }

    @Override
    public TicketResponse searchTicketByCode(String code) {
        Tickets ticket = ticketsRepository.findByJourneyCode(code).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
        TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));
//        Trips trip = tripRepository.findByTicketSegmentId(ticketSegment.getId());
        TicketPay ticketPay = ticketPayRepository.findByTicketId(ticket.getId());
        TicketSegmentResponse ticketSegmentResponse = toTicketSegmentResponse(ticketSegment, ticketPay.getAmount(), LocalDateTime.now());
        return TicketResponse.builder()
                .ticketId(ticket.getId())
                .journeyCode(ticket.getJourneyCode())
                .ticketOwnerName(ticket.getAccount().getFullName())
                .ticketOwnerPhone(ticket.getAccount().getPhone())
                .bookingTime(ticket.getBookingTime())
                .status(ticket.getStatus().getName())
                .ticketSegmentResponse(ticketSegmentResponse)
                .build();
    }

    @Override
    public TicketResponse searchTicketById(Integer ticketId) {
        Account account = getAccountContext();
        Tickets ticket = ticketsRepository.findById(ticketId).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));

        if (!account.getRole().equals(Role.STAFF)) {
            if (!ticket.getAccount().getId().equals(account.getId())) {
                throw new AppException(ErrorCode.UNAUTHORIZED_CLIENT);
            }
        }

        TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));
//        Trips trip = tripRepository.findByTicketSegmentId(ticketSegment.getId());
        TicketPay ticketPay = ticketPayRepository.findByTicketId(ticket.getId());
        TicketSegmentResponse ticketSegmentResponse = toTicketSegmentResponse(ticketSegment, ticketPay.getAmount(), LocalDateTime.now());
        return TicketResponse.builder()
                .ticketId(ticket.getId())
                .journeyCode(ticket.getJourneyCode())
                .ticketOwnerName(ticket.getAccount().getFullName())
                .ticketOwnerPhone(ticket.getAccount().getPhone())
                .bookingTime(ticket.getBookingTime())
                .status(ticket.getStatus().getName())
                .ticketSegmentResponse(ticketSegmentResponse)
                .build();
    }

//    @Override
//    public PaymentProcessResponse changeTicket(ChangeTicketRequest request) throws Exception {
//        Account account = getAccountContext();
//        Tickets ticket = ticketsRepository.findById(request.getTicketId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
//
//        if (!account.getRole().equals(Role.STAFF)) {
//            if (!ticket.getAccount().getId().equals(account.getId())) {
//                throw new AppException(ErrorCode.UNAUTHORIZED_CLIENT);
//            }
//        }
//
//        if(ticket.getStatus().equals(TicketStatus.USED) || ticket.getStatus().equals(TicketStatus.CANCEL)){
//            throw new AppException(ErrorCode.TICKET_IS_USED_OR_CANCELLED);
//        }
//
//        TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(request.getTicketId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));
//
//        RouteStations fromRouteStations = routeStationRepository.findByStationIdAndRouteId(request.getFrom(), request.getTo()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));
//        RouteStations toRouteStations = routeStationRepository.findByStationIdAndRouteId(request.getFrom(), request.getTo()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));
//
//        Seats newSeat = seatRepository.findById(request.getSeatId()).orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));
//        if(!newSeat.getSeatStatus().equals(SeatStatus.AVAILABLE)){
//            throw new AppException(ErrorCode.SEAT_NOT_AVAILABLE);
//        }
//
//        float priceDifference = request.getTotalPrice() - ticketSegment.getPrice();
//
//        Seats prevSeat = ticketSegment.getSeat();
//        prevSeat.setSeatStatus(SeatStatus.AVAILABLE);
//        seatRepository.save(prevSeat);
//
//        newSeat.setSeatStatus(SeatStatus.PENDING);
//        ticketSegment.setSeat(newSeat);
//        ticketSegment.setFromStation(fromRouteStations);
//        ticketSegment.setToStation(toRouteStations);
//        ticketSegmentRepository.save(ticketSegment);
//
//        ticket.setBookingTime(LocalDateTime.now());
//        ticketsRepository.save(ticket);
//
//        if(priceDifference < 0){
//            return null;
//        } else {
//            Payment payment = paymentService.createPaymentForSubPay(priceDifference);
//            SubPay subPay = SubPay.builder()
//                    .type(PaymentType.ADJUSTMENT_PAY)
//                    .amount(priceDifference)
//                    .description(PaymentType.ADJUSTMENT_PAY.getDescription())
//                    .payment(payment)
//                    .oldSeat(prevSeat)
//                    .newSeat(newSeat)
//                    .build();
//            subPayRepository.save(subPay);
//            AdjusmentPaymentRequest adjusmentPaymentRequest = AdjusmentPaymentRequest.builder()
//                    .paymentId(payment.getId())
//                    .totalPrice(priceDifference)
//                    .build();
//            return paymentTicketService.createAdjusmentPaymentTicket(adjusmentPaymentRequest);
//        }
//    }

    @Override
    public String cancelTicket(Integer ticketId) {
        Account account = getAccountContext();
        Tickets ticket = ticketsRepository.findById(ticketId).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));

        if (!account.getRole().equals(Role.STAFF)) {
            if (!ticket.getAccount().getId().equals(account.getId())) {
                throw new AppException(ErrorCode.UNAUTHORIZED_CLIENT);
            }
        }

        TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));
        if(!ticketSegment.getTrip().getStatus().equals(TripsStatus.SCHEDULED)){
            throw new AppException(ErrorCode.TICKET_CAN_NOT_CANCEL);
        }

        if (ChronoUnit.HOURS.between(LocalDateTime.now(), ticketSegment.getTrip().getDepartureTime()) < 6) {
            throw new AppException(ErrorCode.TICKET_CAN_NOT_CANCEL);
        }
        
        ticket.setStatus(TicketStatus.CANCELLED);
        ticketsRepository.save(ticket);

        Seats seat = ticketSegment.getSeat();
        seat.setSeatStatus(SeatStatus.AVAILABLE);
        seatRepository.save(seat);

        return "Success";
    }

    private TicketSegmentResponse toTicketSegmentResponse(TicketSegment ticketSegment, Float amount, LocalDateTime departureTime) {
        Routes route = routeRepository.findById(ticketSegment.getTrip().getRoute().getId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));

        return TicketSegmentResponse.builder()
                    .tripId(ticketSegment.getTrip().getId())
                .seatCode(ticketSegment.getSeat().getSeatCode())
                .departureTime(ticketSegment.getTrip().getDepartureTime())
                .fromStationName(ticketSegment.getFromStation().getStation().getName())
                    .toStationName(ticketSegment.getToStation().getStation().getName())
                .price(amount)
                .build();
    }
}
