package com.sopt.bbangzip.domain.piece.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PieceDeleteRequestDto(
        @NotNull Long subjectId,
        @NotBlank String subjectName,
        @NotBlank String examName,
        @NotEmpty List<Long> pieceIds
) {
}
