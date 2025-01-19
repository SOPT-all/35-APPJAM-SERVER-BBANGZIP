package com.sopt.bbangzip.domain.piece.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record TodoPiecesResponse(
        Integer todayCount,
        Integer completeCount,
        List<TodoPieceDto> todoPiecesList,
        Integer pendingCount
) {
    @Builder
    public record TodoPieceDto(
            Long pieceId,
            String subjectName,
            String examName,
            String studyContents,
            int startPage,
            int finishPage,
            String deadline,
            int remainingDays,
            boolean isFinished
    ){
    }
}