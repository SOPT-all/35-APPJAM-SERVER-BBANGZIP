package com.sopt.bbangzip.domain.piece.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AddTodoPiecesResponse(
        int todoCount,
        List<TodoList> todoList
) {
    @Builder
    public record TodoList(
            long pieceId,
            String subjectName,
            String examName,
            String studyContents,
            int startPage,
            int finishPage,
            String deadline,
            int remainingDays
    ){
    }
}
