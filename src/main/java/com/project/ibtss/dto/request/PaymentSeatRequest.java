package com.project.ibtss.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
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
