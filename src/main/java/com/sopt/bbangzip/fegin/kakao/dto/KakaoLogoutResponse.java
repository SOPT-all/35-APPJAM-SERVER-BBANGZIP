package com.sopt.bbangzip.fegin.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoLogoutResponse(
        Long id // 로그아웃된 사용자의 회원번호
) {
}
