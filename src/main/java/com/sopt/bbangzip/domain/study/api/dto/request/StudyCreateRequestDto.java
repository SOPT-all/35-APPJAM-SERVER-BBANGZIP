package com.sopt.bbangzip.domain.study.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record StudyCreateRequestDto(
        @NotNull Long subjectId,
        @NotBlank String examName,
        @NotBlank String examDate,
        @NotBlank String studyContents,
        @NotEmpty List<PieceRequestDto> pieceList
) {
    @Builder
    public record PieceRequestDto(
            @NotNull int startPage,
            @NotNull int finishPage,
            @NotBlank String deadline
    ) {}
}



