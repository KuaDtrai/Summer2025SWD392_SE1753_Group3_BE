package com.project.ibtss.exception;

import com.project.ibtss.dto.response.ApiResponse;
import com.project.ibtss.enums.ErrorCode;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleException(Exception exception) {
        log.error("AppException occurred: {}", ErrorCode.RUNTIME_EXCEPTION.getCode(), exception);
        log.error("Uncaught exception occurred", exception);
        return ResponseEntity.badRequest()
                                .body(ApiResponse.builder()
                                                    .code(ErrorCode.RUNTIME_EXCEPTION.getCode())
                                                    .message(exception.getMessage())
                                                    .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException appException) {
        ErrorCode errorCode = appException.getErrorCode();
        log.error("AppException occurred: {}", errorCode, appException);
        return returnMethod(errorCode);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e) {

        }
        log.error("AppException occurred: {}", errorCode, exception);
        return returnMethod(errorCode);
    }


    private ResponseEntity<ApiResponse> returnMethod(ErrorCode errorCode) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }
}
