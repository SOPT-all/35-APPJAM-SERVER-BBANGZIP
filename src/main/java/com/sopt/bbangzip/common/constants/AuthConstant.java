package com.sopt.bbangzip.common.constants;

public class AuthConstant {
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String CHARACTER_TYPE = "utf-8";
    public static final String CONTENT_TYPE = "application/json";

    /*
    인증(회원가입, 로그인) 전 단계의 API 들을 화이트리스트에 등록.
    이 배열에 포함된 경로들은 인증 없이 접근이 가능합니다.
    현재는 구현 중이기 때문에, 모든 경로에 대해서 허용해 준 상태입니다!
     */
    public static final String[] AUTH_WHITE_LIST = {
            "/api/v1/**"
    };

    private AuthConstant() { // 이 클래스의 인스턴스 생성을 막기 위해 private 생성자
    }
}
