package com.project.ibtss.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailResponse {
    String email;
    String fullName;
    String phone;
    String dob;
    String address;
    String gender;
    String role;
    String createdDate;
}
