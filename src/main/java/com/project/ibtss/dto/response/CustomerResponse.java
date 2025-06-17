package com.project.ibtss.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String address;
}
