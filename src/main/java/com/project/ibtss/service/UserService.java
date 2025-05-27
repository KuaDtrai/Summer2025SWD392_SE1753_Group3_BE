package com.project.ibtss.service;

import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.response.UserResponse;

public interface UserService {
    UserResponse userDetail();
    UserResponse login(LoginRequest loginRequest);
    String register(RegisterRequest registerRequest);
}
