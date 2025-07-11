package com.project.ibtss.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripAutoGenerateRequest {
    Integer routeId;
    String startDate;
    String endDate;
    Integer startHour;
    Integer endHour;
    Float price;
}
