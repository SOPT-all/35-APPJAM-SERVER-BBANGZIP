package com.sopt.bbangzip.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        // 필터나 다른 로직에서 request.setAttribute("exception", ...)로
        // 미리 설정해둔 에러 코드를 가져와 본다.
        BbangzipErrorCode errorCode = (BbangzipErrorCode) request.getAttribute("exception");

        // 만약 에러 코드가 없으면 기본값으로 처리
        if (errorCode == null) {
            // 에러 코드 enum 중 'WRONG_ENTRY_POINT' 같은 것을 하나 기본값으로 둔다고 가정
            errorCode = ErrorCode.WRONG_ENTRY_POINT;
        }

        // JSON 형태로 응답 내려주기
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(
                objectMapper.writeValueAsString(ResponseDto.fail(errorCode))
        );
    }
}
