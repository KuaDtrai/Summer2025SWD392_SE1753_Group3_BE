package com.project.ibtss.controller;

import com.project.ibtss.enums.Position;
import com.project.ibtss.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverResponse {
    Integer id;
    String email;
    String fullName;
    String phone;
}
