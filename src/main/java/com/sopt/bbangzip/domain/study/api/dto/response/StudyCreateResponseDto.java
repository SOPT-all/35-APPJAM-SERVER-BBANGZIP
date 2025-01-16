package com.sopt.bbangzip.domain.study.api.dto.response;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.study.entity.Study;
import lombok.Builder;

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

    @Builder
    public StudyCreateResponseDto(Long studyId, Long examId, String studyContents, int startPage, int finishPage, String examDate) {
        this.studyId = studyId;
        this.examId = examId;
        this.studyContents = studyContents;
        this.startPage = startPage;
        this.finishPage = finishPage;
        this.examDate = examDate;
    }

    public static StudyCreateResponseDto from(Study study, Exam exam, List<Piece> pieces) {
        return StudyCreateResponseDto.builder()
                .studyId(study.getId())
                .examId(exam.getId())
                .studyContents(study.getStudyContents())
                .startPage(pieces.stream().mapToInt(Piece::getStartPage).min().orElse(0))
                .finishPage(pieces.stream().mapToInt(Piece::getFinishPage).max().orElse(0))
                .examDate(exam.getExamDate().format(DATE_FORMATTER))
                .build();
    }
}
