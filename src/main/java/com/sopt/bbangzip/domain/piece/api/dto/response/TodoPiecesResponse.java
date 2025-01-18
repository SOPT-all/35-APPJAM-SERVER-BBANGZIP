package com.sopt.bbangzip.domain.piece.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record TodoPiecesResponse(
        Integer toadyCount,
        Integer completeCount,
        List<TodoPieceDto> todoPieceStoList,
        Integer pendingCount
) {
    @Builder
    public record TodoPieceDto(
            Long pieceId,
            String subjectName,
            String examName,
            String studyCounts,
            int startPage,
            int finishPage,
            String deadline,
            int remainingDays,
            boolean isFinished
    ){
    }
}