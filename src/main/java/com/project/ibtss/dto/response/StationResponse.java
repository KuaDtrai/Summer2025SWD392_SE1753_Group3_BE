package com.project.ibtss.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationResponse {
    Integer id;
    String name;
    String address;
    LocalDateTime createdDate;
}