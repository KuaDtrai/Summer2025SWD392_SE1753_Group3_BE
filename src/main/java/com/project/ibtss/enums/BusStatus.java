package com.project.ibtss.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum BusStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    MAINTENANCE("MAINTENANCE"),
    ;
    String name;
}