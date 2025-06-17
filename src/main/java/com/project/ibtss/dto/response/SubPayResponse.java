package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class SubPayResponse {
    private Integer id;
    private String type;
    private Float amount;
    private String description;
}
