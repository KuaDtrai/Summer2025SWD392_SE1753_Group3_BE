package com.project.ibtss.utilities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum PaymentStatus {
    PAID ("PAID"),
    FAILED ("FAILED"),
    CANCELLED ("CANCELLED"),
    PENDING("PENDING")

    ;
    String name;
}
