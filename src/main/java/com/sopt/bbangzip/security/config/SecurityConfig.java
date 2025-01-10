package com.sopt.bbangzip.security.config;

import com.sopt.bbangzip.common.constants.AuthConstant;
import com.sopt.bbangzip.security.exception.JwtAuthenticationEntryPoint;
import com.sopt.bbangzip.security.filter.ExceptionHandlerFilter;
import com.sopt.bbangzip.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // API 서버 구성하므로 csrf 보호 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                // 폼 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션 사용 안함 == stateless
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인증 실패 시 예외 처리를 위해, 앞서 만들어 두었던 handler 등록!
                .exceptionHandling(exception ->
                {
                    exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                });


        // 화이트리스트에 등록한 요청의 경우엔 허용, 나머지 API 들에 대해서는 인증 진행한다.
        http.authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(AuthConstant.AUTH_WHITE_LIST).permitAll()
                            .anyRequest()
                            .authenticated();
        })
                // 필터 등록
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class);

        return http.build();
    }
}