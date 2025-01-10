package com.sopt.bbangzip.security.jwt;

import com.sopt.bbangzip.common.exception.code.BbangzipErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionLogger {

    public void log(BbangzipErrorCode errorCode, Exception e) {
        if (errorCode.getHttpStatus().is4xxClientError()) {
            log.warn("Client error [{}]: {}", errorCode.getCode(), e.getMessage());
        } else if (errorCode.getHttpStatus().is5xxServerError()) {
            log.error("Server error [{}]: {}", errorCode.getCode(), e.getMessage(), e);
        }
    }
}