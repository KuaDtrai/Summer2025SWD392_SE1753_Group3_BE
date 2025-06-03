package com.project.ibtss.dto.response;

import com.project.ibtss.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    String username;
    String email;
    String fullName;
    Role role;
    String token;
}
