package com.sopt.bbangzip.domain.subject.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SubjectNameOrMotivationMessageDto(
        @NotBlank String value
) {
}
