package com.project.ibtss.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PaymentSeatRequest {
    int tripId;
    List<Integer> seatIds;
    String fullName;
    String phone;
    String email;
    Integer pickupLocationId;
    Integer dropoffLocationId;
    LocalDateTime departureTime;
    Float totalPrice;
}
