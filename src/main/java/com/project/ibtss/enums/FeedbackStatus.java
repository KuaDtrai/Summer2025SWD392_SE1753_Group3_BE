package com.project.ibtss.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum FeedbackStatus {
    PENDING("PENDING"),
    RESOLVED("RESOLVED")
    ;
    String name;
}
