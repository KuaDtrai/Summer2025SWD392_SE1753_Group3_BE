package com.project.ibtss.dto.response;

import com.project.ibtss.utilities.enums.SeatStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatForSelectResponse {
    int busId;
    int seatId;
    String seatCode;
    SeatStatus seatStatus;
}
