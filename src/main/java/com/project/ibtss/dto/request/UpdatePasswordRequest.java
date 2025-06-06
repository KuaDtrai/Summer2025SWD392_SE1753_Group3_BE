package com.project.ibtss.dto.request;

import lombok.Getter;

@Getter

public class UpdatePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
