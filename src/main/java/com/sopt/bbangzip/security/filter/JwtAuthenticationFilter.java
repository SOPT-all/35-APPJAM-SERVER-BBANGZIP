package com.sopt.bbangzip.security.filter;

import com.sopt.bbangzip.security.jwt.JwtTokenProvider;
import com.sopt.bbangzip.security.jwt.UserAuthenticationFactory;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserAuthenticationFactory userAuthenticationFactory;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // 요청의 헤더에서 토큰 찾아옴
        final String token = jwtTokenProvider.getJwtFromRequest(request);
        //토큰 있으면 -> 토큰으로부터 유저 정보 가져와서 -> 인증 객체 생성
        if (StringUtils.hasText(token)) {
            log.info("====================token: {}", token);

            Claims claims = jwtTokenProvider.getBody(token);
            log.info("====================claim: {}", claims);

            userAuthenticationFactory.authenticateUser(claims, request);
        }
        filterChain.doFilter(request, response);
    }
}