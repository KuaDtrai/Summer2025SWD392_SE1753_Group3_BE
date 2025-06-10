package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationResponse {
    Integer id;
    String name;
    String address;
}
