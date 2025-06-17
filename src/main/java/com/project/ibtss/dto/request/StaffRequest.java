package com.project.ibtss.dto.request;

import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.enums.Position;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StaffRequest {
    private Integer accountId;
    private Position position;
    private LocalDate hireDate;
}
