package com.sopt.bbangzip.domain.study.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StudyCreateRequestDto(
        @NotNull Long subjectId,
        @NotBlank String subjectName,
        @NotBlank String examName,
        @NotNull String examDate,
        @NotBlank String studyContents,
        @NotEmpty List<PieceRequestDto> pieceList
) {
    public record PieceRequestDto(
            @NotNull int startPage,
            @NotNull int finishPage,
            @NotBlank String deadline
    ) {}
}



