package com.project.ibtss.controller;

import com.project.ibtss.dto.request.LoginRequest;
import com.project.ibtss.dto.request.RegisterRequest;
import com.project.ibtss.dto.response.AccountResponse;
import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.repository.AccountRepository;
import com.project.ibtss.repository.TokenRepository;
import com.project.ibtss.service.JWTService;
import com.project.ibtss.service_implement.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/authen/")
public class AuthController {

    private final AccountServiceImpl accountService;
    private final JWTService jwtService;
//    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AccountRepository accountRepository;
    @PostMapping("/login")
    public ApiResponse<AccountResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<AccountResponse>builder()
                                        .code(HttpStatus.OK.value())
                                        .message(HttpStatus.OK.getReasonPhrase())
                                        .data(accountService.login(loginRequest)).build();
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest registerRequest) {
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(accountService.register(registerRequest))
                .build();
    }

    //check jwt
    @GetMapping()
    public ApiResponse<AccountResponse> userDetail() {
        return ApiResponse.<AccountResponse>builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(accountService.accountDetail())
                .build();
    }

//    @PostMapping("/refresh")
//    public ApiResponse<?> refreshAccessToken(HttpServletRequest request) {
//        final String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new AppException(ErrorCode.INVALID_AUTHORIZATION_HEADER);
//        }
//
//        String refreshToken = authHeader.substring(7);
//        String email = jwtService.extractEmail(refreshToken);
//
//        if (email == null) {
//            throw new AppException(ErrorCode.INVALID_REFRESH_TOKEN);
//        }
//
//        var account = accountRepository.findByFullName(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        var storedRefreshToken = tokenRepository.findByValueAndTypeAndUser(
//                refreshToken, TokenType.REFRESH.getValue(), user
//        );
//
//        if (storedRefreshToken.isEmpty() || storedRefreshToken.get().getStatus().equals(TokenStatus.EXPIRED.getValue())) {
//            throw new AppException(ErrorCode.REFRESH_TOKEN_UNKNOWN_ERROR);
//        }
//
//        String newAccessToken = jwtService.generateAccessToken(user);
//
//        tokenRepository.save(
//                Token.builder()
//                        .value(newAccessToken)
//                        .type(TokenType.ACCESS.getValue())
//                        .status(TokenStatus.ACTIVE.getValue())
//                        .user(user)
//                        .build()
//        );
//
//        return ApiResponse.builder()
//                .code(HttpStatus.OK.value())
//                .message(HttpStatus.OK.getReasonPhrase())
//                .data(new TokenResponse(newAccessToken))
//                .build();
//    }
}
