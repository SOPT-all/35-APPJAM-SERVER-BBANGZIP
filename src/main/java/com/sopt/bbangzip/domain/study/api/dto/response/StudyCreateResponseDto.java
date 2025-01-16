package com.sopt.bbangzip.domain.study.api.dto.response;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.study.entity.Study;

import java.time.format.DateTimeFormatter;
import java.util.List;

public record StudyCreateResponseDto(
        Long studyId,
        Long examId,
        String studyContents,
        int startPage,
        int finishPage,
        String examDate
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년MM월dd일");

    public StudyCreateResponseDto(Study study, Exam exam, List<Piece> pieces) {
        this(
                study.getId(),
                exam.getId(),
                study.getStudyContents(),
                pieces.stream().mapToInt(Piece::getStartPage).min().orElse(0),
                pieces.stream().mapToInt(Piece::getFinishPage).max().orElse(0),
                exam.getExamDate().format(DATE_FORMATTER) // LocalDate -> String 변환
        );
    }
}
