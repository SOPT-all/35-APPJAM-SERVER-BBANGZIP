package com.sopt.bbangzip.fegin.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUnlinkResponse(
        Long id // 연결 끊기에 성공한 사용자의 회원번호
) {
}
