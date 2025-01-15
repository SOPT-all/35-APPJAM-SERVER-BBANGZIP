package com.sopt.bbangzip.domain.token.api;

import lombok.Builder;

@Builder
public record ReissueJwtTokensDto(
        String accessToken,
        String refreshToken
) {
}