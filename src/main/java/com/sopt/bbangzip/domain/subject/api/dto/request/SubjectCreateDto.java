package com.sopt.bbangzip.domain.subject.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubjectCreateDto(
        @NotNull int year, // 년도
        @NotBlank String semester, // 학기 (1, 2 등)
        @NotBlank String subjectName // 과목명
) {}
