package com.sopt.bbangzip.fegin.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokenResponse(
        String tokenType,
        String accessToken,
        Integer expiresIn,
        String refreshToken,
        Integer refreshTokenExpiresIn
) {
}
