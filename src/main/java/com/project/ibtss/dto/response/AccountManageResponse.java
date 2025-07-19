package com.project.ibtss.dto.response;

import com.project.ibtss.utilities.enums.Gender;
import com.project.ibtss.utilities.enums.Position;
import com.project.ibtss.utilities.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountManageResponse {
    Integer id;
    String email;
    String fullName;
    String phone;
    Role role;
    Boolean isActive;
    LocalDate createdDate;
    Position position;
    LocalDate dateOfBirth;
    String address;
    Gender gender;
}
