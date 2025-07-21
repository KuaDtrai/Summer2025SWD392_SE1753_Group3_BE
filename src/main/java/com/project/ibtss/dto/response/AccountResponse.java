package com.project.ibtss.dto.response;

import com.project.ibtss.utilities.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    Integer id;
    String email;
    String fullName;
    String phone;
    Role role;
    String accessToken;
}
