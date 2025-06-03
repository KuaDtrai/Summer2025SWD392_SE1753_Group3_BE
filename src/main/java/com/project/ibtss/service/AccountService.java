package com.project.ibtss.service;

import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.dto.response.UserResponse;

//@Service
public interface AccountService {
    AccountResponse login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    AccountResponse accountDetail();
}
