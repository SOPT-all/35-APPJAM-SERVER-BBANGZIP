package com.sopt.bbangzip.security.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 토근의 인증을 위한 사용자 정의 Authentication 객체 (서비스 인증 주채)
  */
public class UserAuthentication extends AbstractAuthenticationToken {

    private final Long userId;

    public UserAuthentication(Long userId) {
        super(null); // authorities를 null로 처리
        this.userId = userId;
        setAuthenticated(true); // 인증된 상태로 설정
    }

    @Override
    public Object getPrincipal() {
        return userId; // principal로 userId 반환
    }

    @Override
    public Object getCredentials() {
        return null; // credentials는 사용하지 않음
    }

    // userId 만 사용해서 UserAuthentication 객체 생성
    public static UserAuthentication createUserAuthentication(Long userId) {
        return new UserAuthentication(userId);
    }
}