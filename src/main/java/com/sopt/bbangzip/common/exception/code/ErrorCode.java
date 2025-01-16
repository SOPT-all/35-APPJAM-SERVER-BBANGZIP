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
    WRONG_ENTRY_POINT(HttpStatus.BAD_REQUEST, "error", "잘못된 요청입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "error", "올바르지 않은 형식의 토큰입니다"),
    INVALID_OPTION(HttpStatus.BAD_REQUEST, "error", "올바르지 않은 옵션입니다."),

    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "error", "인증되지 않은 사용자입니다."),
    MALFORMED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "error", "잘못된 형식의 토큰입니다."),
    TYPE_ERROR_JWT_TOKEN(HttpStatus.BAD_REQUEST, "error", "올바른 타입의 JWT 토큰이 아닙니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "error", "만료된 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "error", "유효하지 않은 리프레시 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "error", "지원하지 않는 형식의 토큰입니다."),
    UNKNOWN_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "error", "알 수 없는 인증 토큰 오류가 발생했습니다."),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "error", "접근 권한이 없습니다."),

    // 404
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "error", "존재하지 않는 API입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"error","존재하지 않는 사용자입니다."),
    DUPLICATED_SUBJECT(HttpStatus.NOT_FOUND, "error", "해당 학기에 과목명이 중복됩니다."),
    NOT_FOUND_USER_SUBJECT(HttpStatus.NOT_FOUND,"error","해당 학기에 유저가 등록한 과목이 없습니다"),
    NOT_FOUND_SUBJECT(HttpStatus.NOT_FOUND, "error", "존재하지 않는 과목입니다."),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "error", "존재하지 않는 토큰입니다"),
    NOT_FOUND_STUDY(HttpStatus.NOT_FOUND, "error", "존재하지 않는 공부범위(교재)입니다"),
    NOT_FOUND_EXAM(HttpStatus.NOT_FOUND, "error", "존재하지 않는 시험입니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error", "서버 내부 오류입니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
