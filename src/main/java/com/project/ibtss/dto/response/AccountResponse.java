package com.project.ibtss.dto.response;

import com.project.ibtss.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    Integer id;
//    String username;
    String email;
    String fullName;
    String phone;
    Role role;
    String accessToken;
}
