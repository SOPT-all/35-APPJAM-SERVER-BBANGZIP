package com.sopt.bbangzip.domain.token.api;

import lombok.Builder;

@Builder
public record JwtTokensDto(
        String accessToken,
        String refreshToken,
        Boolean isOnboardingComplete
) { }
