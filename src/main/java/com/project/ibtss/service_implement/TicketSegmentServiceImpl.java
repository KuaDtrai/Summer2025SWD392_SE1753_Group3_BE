package com.project.ibtss.service_implement;

import com.project.ibtss.dto.request.PaymentConfirmRequest;
import com.project.ibtss.dto.request.PaymentSeatRequest;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.*;
import com.project.ibtss.repository.*;
import com.project.ibtss.service.TicketSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketSegmentServiceImpl implements TicketSegmentService {

    private final RouteStationRepository routeStationRepository;
    private final TicketSegmentRepository ticketSegmentRepository;
    private final TripRepository tripRepository;
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;

    @Override
    public TicketSegment createTicketSegment(PaymentSeatRequest request, Tickets ticket, Seats seat) {

        Stations fromStation = stationRepository.findById(request.getPickupLocationId()).orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_EXISTED));
        Stations toStation = stationRepository.findById(request.getDropoffLocationId()).orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_EXISTED));

        Trips trip = tripRepository.findById(request.getTripId()).orElseThrow(() -> new AppException(ErrorCode.TRIP_NOT_EXISTED));

        RouteStations fromRouteStation = routeStationRepository.findByStationIdAndRouteId(fromStation.getId(), trip.getId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));
        RouteStations toRouteStations = routeStationRepository.findByStationIdAndRouteId(toStation.getId(), trip.getId()).orElseThrow(() -> new AppException(ErrorCode.ROUTE_STATION_NOT_EXISTED));

        int totalTicket = request.getSeatIds().size();
        float price = request.getTotalPrice() / totalTicket;

        TicketSegment ticketSegment =  TicketSegment.builder()
                .price(price)
                .fromStation(fromRouteStation)
                .toStation(toRouteStations)
                .seat(seat)
                .ticket(ticket)
                .trip(trip)
                .build();
        return ticketSegmentRepository.save(ticketSegment);
    }
}
