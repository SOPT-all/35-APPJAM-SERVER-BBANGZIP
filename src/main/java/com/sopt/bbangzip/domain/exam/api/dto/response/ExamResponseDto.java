package com.sopt.bbangzip.domain.exam.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ExamResponseDto(
        String motivationMessage,
        int examDDay,
        String examDate,
        List<StudyPiece> studyList
) {
    @Builder
    public record StudyPiece(
            Long pieceId,
            String studyContents,
            int startPage,
            int finishPage,
            String deadline,
            int remainingDays,
            boolean isFinished
    ) {}
}
