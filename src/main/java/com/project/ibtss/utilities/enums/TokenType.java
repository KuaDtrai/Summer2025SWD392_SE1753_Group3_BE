package com.project.ibtss.utilities.enums;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum TokenType {
    ACCESS("access"),
    REFRESH("refresh");

    String value;
}
