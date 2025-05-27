package com.project.ibtss.dto.response;

import com.project.ibtss.enums.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;
    String email;
    String fullName;
    Role role;
    String token;
}
