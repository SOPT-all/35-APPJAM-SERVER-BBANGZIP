package com.sopt.bbangzip.domain.piece.api.dto.request;


import jakarta.validation.constraints.NotNull;

public record IsFinishedDto(
        @NotNull
        Boolean isFinished
) {
}