package com.project.ibtss.enums;

import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    RUNTIME_EXCEPTION(999, "Uncaught exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(100, "Uncaught exception", HttpStatus.BAD_REQUEST),
    INVALID_REFRESH_TOKEN(101, "Invalid refresh token", HttpStatus.UNAUTHORIZED),
    INVALID_AUTHORIZATION_HEADER(102, "Invalid authorization header", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_UNKNOWN_ERROR(103, "Refresh token expired or invalid", HttpStatus.UNAUTHORIZED),

    USER_NOT_FOUND(201, "User not found", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD(202, "Incorrect password", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(203, "Password and Confirm Password do not match", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTED(204, "Username already exists", HttpStatus.BAD_REQUEST),

    ;
    int code;
    String message;
    HttpStatusCode statusCode;
}
