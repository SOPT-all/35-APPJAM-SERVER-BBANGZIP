package com.sopt.bbangzip.common.advice;

import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.common.exception.code.BbangzipErrorCode;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(
        basePackages = "com.sopt.bbangzip"
)
public class ResponseDtoAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
        return !(returnType.getParameterType() == ResponseDto.class)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        // 이미 ResponseDto로 감싸진 경우 그대로 반환
        if (body instanceof ResponseDto) {
            return body;
        }
        if (body instanceof BbangzipErrorCode) {
            return ResponseDto.fail((BbangzipErrorCode) body);
        }
        return ResponseDto.success(body);
    }
}
