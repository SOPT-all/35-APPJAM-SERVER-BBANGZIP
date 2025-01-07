package com.sopt.bbangzip.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sopt.bbangzip.common.exception.code.BbangzipErrorCode;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseDto<T> (
        String code,
        T data,
        String message
) {
    public static <T> ResponseDto<T> success(final T data) {
        return new ResponseDto<>("success", data, null);
    }

    public static <T> ResponseDto<T> fail(BbangzipErrorCode code) {
        return new ResponseDto<>(code.getCode(), null, code.getMessage());
    }
}