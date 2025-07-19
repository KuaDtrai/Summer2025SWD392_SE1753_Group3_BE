package com.project.ibtss.utilities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum SeatStatus {
    AVAILABLE("AVAILABLE"),
    PENDING("PENDING"),
    BOOKED("BOOKED")
    ;
    String name;
}
