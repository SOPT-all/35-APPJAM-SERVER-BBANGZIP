package com.sopt.bbangzip.security.filter;

import com.sopt.bbangzip.common.exception.base.BusinessException;
import com.sopt.bbangzip.common.exception.code.BbangzipErrorCode;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.security.jwt.ErrorResponseWriter;
import com.sopt.bbangzip.security.jwt.ExceptionLogger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// 1번째로 구현
@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ExceptionLogger exceptionLogger;
    private final ErrorResponseWriter errorResponseWriter;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (AuthenticationException authEx) {
            // AuthenticationException이 발생하면, EntryPoint가 대신 처리하도록 요청 속성에 세팅 후 재던짐
            BbangzipErrorCode errorCode = mapExceptionToErrorCode(authEx);
            request.setAttribute("exception", errorCode);

            // Spring Security 흐름상 EntryPoint로 넘기기 위해 예외를 다시 던진다
            throw authEx;
        } catch (Exception e) {
            // 나머지 예외는 여기서 직접 JSON 응답 작성
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) {
        BbangzipErrorCode errorCode = mapExceptionToErrorCode(e);
        exceptionLogger.log(errorCode, e);
        errorResponseWriter.write(response, errorCode);
    }

    private BbangzipErrorCode mapExceptionToErrorCode(Exception e) {
        if (e instanceof MalformedJwtException) return ErrorCode.MALFORMED_JWT_TOKEN;
        if (e instanceof IllegalArgumentException) return ErrorCode.TYPE_ERROR_JWT_TOKEN;
        if (e instanceof ExpiredJwtException) return ErrorCode.EXPIRED_JWT_TOKEN;
        if (e instanceof UnsupportedJwtException) return ErrorCode.UNSUPPORTED_JWT_TOKEN;
        if (e instanceof JwtException) return ErrorCode.UNKNOWN_JWT_TOKEN;
        if (e instanceof BusinessException) return ((BusinessException) e).getErrorCode();
        return ErrorCode.INTERNAL_SERVER_ERROR;
    }
}