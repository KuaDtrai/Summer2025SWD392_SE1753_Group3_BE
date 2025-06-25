package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.ChangeTicketRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.dto.response.TicketResponse;
import com.project.ibtss.dto.response.TicketSegmentResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.TicketStatus;
import com.project.ibtss.enums.TripsStatus;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<TicketResponse> getAllTicketByAccountId(int accountId) {
        List<Tickets> tickets = ticketsRepository.findAllByAccountId(accountId);
        List<TicketResponse> responses = new ArrayList<>();
        for(Tickets ticket : tickets) {
            TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));
//            Trips trip = tripRepository.findByTicketSegmentId(ticketSegment.getId());
            TicketPay ticketPay = ticketPayRepository.findByTicketId(ticket.getId());
            TicketSegmentResponse ticketSegmentResponse = toTicketSegmentResponse(ticketSegment, ticketPay.getAmount(), LocalDateTime.now());
            TicketResponse ticketResponse = TicketResponse.builder()
                    .ticketId(ticket.getId())
                    .journeyCode(ticket.getJourneyCode())
                    .ticketOwnerName(ticket.getAccount().getFullName())
                    .ticketOwnerPhone(ticket.getAccount().getPhone())
                    .bookingTime(ticket.getBookingTime())
                    .status(ticket.getStatus().getName())
                    .ticketSegmentResponse(ticketSegmentResponse)
                    .build();

            responses.add(ticketResponse);
        }
        return responses;
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
        Tickets ticket = ticketsRepository.findById(ticketId).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
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
    public String cancelTicket(Integer ticketId) {
        Tickets ticket = ticketsRepository.findById(ticketId).orElseThrow(() -> new AppException(ErrorCode.TICKET_NOT_EXISTED));
        TicketSegment ticketSegment = ticketSegmentRepository.findByTicketId(ticket.getId()).orElseThrow(() -> new AppException(ErrorCode.TICKET_SEGMENT_NOT_EXISTED));
        if(!ticketSegment.getTrip().getStatus().equals(TripsStatus.WAITING)){
            throw new AppException(ErrorCode.TICKET_CAN_NOT_CANCEL);
        }

        ticket.setStatus(TicketStatus.CANCEL);
        ticketsRepository.save(ticket);

        return "Success";
    }

    private TicketSegmentResponse toTicketSegmentResponse(TicketSegment ticketSegment, Float amount, LocalDateTime departureTime) {
        Routes route = routeRepository.findById(ticketSegment.getTrip().getRoute().getId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED))
            return TicketSegmentResponse.builder()
                .seatCode(ticketSegment.getSeat().getSeatCode())
                .departureTime(ticketSegment.getTrip().getDepartureTime())
                .fromStationName(route.getDepartureStation().getName())
                    .toStationName(route.getDestinationStation().getName())
                .price(amount)
                .build();
    }
}
