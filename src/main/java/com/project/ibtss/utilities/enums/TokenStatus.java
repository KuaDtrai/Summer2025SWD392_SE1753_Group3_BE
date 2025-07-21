package com.project.ibtss.utilities.enums;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TokenStatus {
    ACTIVE("active"),
    EXPIRED("expired"),

    ;
    String value;
}

