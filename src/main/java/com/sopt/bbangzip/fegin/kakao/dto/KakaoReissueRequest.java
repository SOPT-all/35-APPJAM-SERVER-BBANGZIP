package com.sopt.bbangzip.fegin.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoReissueRequest(
      String refreshToken,
      String clientId,
      String grantType
) {
}
