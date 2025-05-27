package com.project.ibtss.controller;

import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.dto.response.TokenResponse;
import com.project.ibtss.dto.response.UserResponse;
import com.project.ibtss.enums.ErrorCode;
import com.project.ibtss.enums.TokenStatus;
import com.project.ibtss.enums.TokenType;
import com.project.ibtss.exception.AppException;
import com.project.ibtss.model.Token;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.repository.UserRepository;
import com.project.ibtss.service.JWTService;
import com.project.ibtss.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<UserResponse>builder()
                                        .code(HttpStatus.OK.value())
                                        .message(HttpStatus.OK.getReasonPhrase())
                                        .data(userService.login(loginRequest))
                                        .build();
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest registerRequest) {
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(userService.register(registerRequest))
                .build();
    }

    //check jwt
    @GetMapping()
    public ApiResponse<UserResponse> userDetail() {
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(userService.userDetail())
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<?> refreshAccessToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.INVALID_AUTHORIZATION_HEADER);
        }

        String refreshToken = authHeader.substring(7);
        String email = jwtService.extractEmail(refreshToken);

        if (email == null) {
            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        var user = userRepository.findByUsername(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var storedRefreshToken = tokenRepository.findByValueAndTypeAndUser(
                refreshToken, TokenType.REFRESH.getValue(), user
        );

        if (storedRefreshToken.isEmpty() || storedRefreshToken.get().getStatus().equals(TokenStatus.EXPIRED.getValue())) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_UNKNOWN_ERROR);
        }

        String newAccessToken = jwtService.generateAccessToken(user);

        tokenRepository.save(
                Token.builder()
                        .value(newAccessToken)
                        .type(TokenType.ACCESS.getValue())
                        .status(TokenStatus.ACTIVE.getValue())
                        .user(user)
                        .build()
        );

        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(new TokenResponse(newAccessToken))
                .build();
    }
}
