package com.project.ibtss.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PaymentType {
    PAY_TICKET("Pay bus ticket"),
    ADJUSTMENT_PAY("Adjustment fee for updated price")
    ;
    String description;
}
