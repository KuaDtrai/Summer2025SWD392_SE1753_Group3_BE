package com.project.ibtss.exception;

import com.project.ibtss.enums.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {
    ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
