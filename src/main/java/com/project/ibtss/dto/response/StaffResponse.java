package com.project.ibtss.dto.response;

import com.project.ibtss.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StaffResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private Position position;
    private LocalDate hireDate;
}
