package com.project.ibtss.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AccountRequest {
    private String email;
//    private String password;
    private String fullName;
    private String phone;
}
