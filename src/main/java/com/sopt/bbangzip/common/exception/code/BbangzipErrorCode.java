package com.sopt.bbangzip.common.exception.code;

import org.springframework.http.HttpStatus;

public interface BbangzipErrorCode {
    HttpStatus getHttpStatus();
    String getCode();
    String getMessage();
}

