package com.sopt.bbangzip.domain.piece.api.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PieceDeleteRequestDto(
        @NotEmpty List<Long> pieceIds
) {
}