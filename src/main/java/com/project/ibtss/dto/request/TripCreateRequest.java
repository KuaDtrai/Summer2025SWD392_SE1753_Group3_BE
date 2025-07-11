package com.project.ibtss.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripCreateRequest {
    Integer routeId;
    String departureTime;
    float price;
    Integer busId;
    Integer accountId;
}
