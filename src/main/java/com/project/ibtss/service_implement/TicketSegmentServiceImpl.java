package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.RouteStationRepository;
import com.project.ibtss.repository.TicketSegmentRepository;
import com.project.ibtss.repository.TripRepository;
import com.project.ibtss.service.TicketSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketSegmentServiceImpl implements TicketSegmentService {

    private final RouteStationRepository routeStationRepository;
    private final TicketSegmentRepository ticketSegmentRepository;
    private final TripRepository tripRepository;

    @Override
    public TicketSegment createTicketSegment(PaymentSeatRequest request, Tickets ticket, Seats seat) {
        RouteStations fromStation = routeStationRepository.findByStationId(request.getPickupLocationId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));
        RouteStations toStation = routeStationRepository.findByStationId(request.getDropoffLocationId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));

        Trips trip = tripRepository.findById(request.getTripId()).orElseThrow(() -> new AppException(ErrorCode.TRIP_NOT_EXISTED));

        int totalTicket = request.getSeatIds().size();
        float price = request.getTotalPrice() / totalTicket;

        TicketSegment ticketSegment =  TicketSegment.builder()
                .price(price)
                .fromStation(fromStation)
                .toStation(toStation)
                .seat(seat)
                .ticket(ticket)
                .trip(trip)
                .build();
        return ticketSegmentRepository.save(ticketSegment);
    }
}
