//package com.project.ibtss.controller;
//
//import com.project.ibtss.dto.request.TicketSegmentRequest;
//import com.project.ibtss.dto.response.*;
//import com.project.ibtss.enums.Position;
//import com.project.ibtss.enums.Role;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/ticketSegment")
//public class TicketSegmentController {
//
//    private TicketSegmentResponse getTicketSegmentResponse() {
//        TripResponse tripResponse = new TripResponse();
//        RouteResponse routeResponse = new RouteResponse();
//        StationResponse stationResponse = new StationResponse();
//        RouteStationResponse routeStationResponse = new RouteStationResponse(1, routeResponse, stationResponse, 1);
//        return new TicketSegmentResponse(1, "Journey code", 1, tripResponse, "Seat code", routeStationResponse, routeStationResponse, 12f);
//    }
//
//    @GetMapping("/{id}")
//    public ApiResponse<TicketSegmentResponse> getTicketResponse(@PathVariable int id) {
//        return ApiResponse.<TicketSegmentResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(getTicketSegmentResponse())
//                .build();
//    }
//
//    @GetMapping("/{ticketId}")
//    public ApiResponse<List<TicketSegmentResponse>> getTicketsResponseByTicketId(@PathVariable int ticketId) {
//        List<TicketSegmentResponse> ticketSegmentResponseList = new ArrayList<>();
//        ticketSegmentResponseList.add(getTicketSegmentResponse());
//        ticketSegmentResponseList.add(getTicketSegmentResponse());
//
//        return ApiResponse.<List<TicketSegmentResponse>>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(ticketSegmentResponseList)
//                .build();
//    }
//
//    @PostMapping
//    public ApiResponse<TicketSegmentResponse> createTicketSegment(@RequestBody TicketSegmentRequest ticketSegmentRequest) {
//        return ApiResponse.<TicketSegmentResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(getTicketSegmentResponse())
//                .build();
//    }
//
//    @PutMapping("/{id}")
//    public ApiResponse<TicketSegmentResponse> updateTicketSegment(@PathVariable int id, @RequestBody TicketSegmentRequest ticketSegmentRequest) {
//        return ApiResponse.<TicketSegmentResponse>builder()
//                .code(HttpStatus.OK.value())
//                .message("")
//                .data(getTicketSegmentResponse())
//                .build();
//    }
//}
