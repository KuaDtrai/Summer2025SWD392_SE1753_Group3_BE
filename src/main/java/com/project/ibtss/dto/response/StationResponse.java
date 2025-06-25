package com.project.ibtss.dto.response;

import com.project.ibtss.enums.StationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationResponse {
    Integer id;
    String name;
    String address;
    String status;
    LocalDateTime createdDate;
}
