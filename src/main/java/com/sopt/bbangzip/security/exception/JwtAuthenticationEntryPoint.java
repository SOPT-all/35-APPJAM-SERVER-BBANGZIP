package com.sopt.bbangzip.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sopt.bbangzip.common.constants.AuthConstant;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.common.exception.code.BbangzipErrorCode;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /*
    필터에서 인증이나 인가 통과하지 못했을 떄, Handle 할 클래스
    인증 실패 시, Unauthorized 에러를 리턴할 EntryPoint
    */

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        BbangzipErrorCode errorCode = (BbangzipErrorCode) request.getAttribute("exception");

        // 만약 에러 코드가 없으면 기본값으로 처리
        if (errorCode == null) {
            errorCode = ErrorCode.WRONG_ENTRY_POINT;
        }

        // JSON 형태로 응답 내려주기
        response.setContentType(AuthConstant.CONTENT_TYPE);
        response.setCharacterEncoding(AuthConstant.CHARACTER_TYPE);
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(
                objectMapper.writeValueAsString(ResponseDto.fail(errorCode))
        );
    }
}
