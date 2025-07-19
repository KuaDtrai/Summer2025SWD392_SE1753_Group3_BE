package com.project.ibtss.utilities.enums;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum Permission{
    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    STAFF_CREATE("staff:create"),
    STAFF_READ("staff:read"),
    STAFF_UPDATE("staff:update"),
    STAFF_DELETE("staff:delete"),

    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),

    ;
    String permission;
}

