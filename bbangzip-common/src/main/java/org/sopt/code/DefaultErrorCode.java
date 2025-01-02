package org.sopt.code;

import org.springframework.http.HttpStatus;

public interface DefaultErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}
