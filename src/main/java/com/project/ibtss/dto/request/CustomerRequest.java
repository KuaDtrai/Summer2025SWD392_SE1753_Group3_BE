package com.project.ibtss.dto.request;

import com.project.ibtss.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class CustomerRequest {
    private Gender gender;
    private LocalDate dateOfBirth;
    private String address;
}
