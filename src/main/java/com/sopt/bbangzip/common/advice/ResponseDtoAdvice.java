package com.sopt.bbangzip.common.advice;

import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.common.exception.code.BbangzipErrorCode;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice(
        basePackages = "com.sopt.bbangzip"
)
public class ResponseDtoAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !(returnType.getParameterType() == ResponseDto.class)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }
    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {
        if (body instanceof BbangzipErrorCode errorCode) {
            return ResponseDto.fail(errorCode);
        }

        // 이미 ResponseDto로 감싸진 경우 그대로 반환
        if (body instanceof ResponseDto) {
            return body;
        }

        // body가 null인 경우 빈 Object로 반환
        if (body == null) {
            return ResponseDto.success(new Object());
        }

        // body가 빈 리스트인 경우 빈 Object로 변환
        if (body instanceof List<?> list && list.isEmpty()) {
            return ResponseDto.success(new Object());
        }

        // 나머지 경우 ResponseDto로 감싸기
        return ResponseDto.success(body);
    }}