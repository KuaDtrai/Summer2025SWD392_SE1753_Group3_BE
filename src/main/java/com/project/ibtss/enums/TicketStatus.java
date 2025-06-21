package com.project.ibtss.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TicketStatus {
    PAID("PAID"),
    PENDING("PENDING"),
    CANCEL("CANCEL")

    ;
    String name;
}
