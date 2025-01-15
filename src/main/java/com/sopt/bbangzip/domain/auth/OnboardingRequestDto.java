package com.sopt.bbangzip.domain.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OnboardingRequestDto(
        @NotBlank
        String nickname,
        @NotNull
        int year,
        @NotBlank
        String semester,
        @NotBlank
        String subjectName
) {
}