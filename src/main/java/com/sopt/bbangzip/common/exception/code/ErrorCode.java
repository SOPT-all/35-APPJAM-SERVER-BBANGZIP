package com.sopt.bbangzip.common.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BbangzipErrorCode{

    // 400
    INVALID_ARGUMENTS(HttpStatus.BAD_REQUEST, "error", "인자의 형식이 올바르지 않습니다."),

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "error", "인증되지 않은 사용자입니다."),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "error", "접근 권한이 없습니다."),

    // 404
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "error", "존재하지 않는 API입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error", "서버 내부 오류입니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
