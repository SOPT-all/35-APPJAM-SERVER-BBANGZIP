package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.common.exception.base.InvalidOptionsException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.exam.api.dto.response.ExamResponseDto;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.piece.service.PieceRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRetriever examRetriever;
    private final PieceRetriever pieceRetriever;

    /**
     * 시험 정보 조회 및 examName 변환
     *
     * @param subjectId 과목 ID
     * @param examName  시험 이름 ("mid" 또는 "fin")
     */
    public ExamResponseDto getExamInfoWithConversion(
            final Long userId,
            final String examName,
            final Long subjectId
    ) {
        String convertedExamName = switch (examName) {
            case "mid" -> "중간고사";
            case "fin" -> "기말고사";
            default -> throw new InvalidOptionsException(ErrorCode.INVALID_ARGUMENTS);
        };

        Exam exam = examRetriever.findBySubjectIdAndExamNameAndUser(userId, convertedExamName, subjectId);

        List<ExamResponseDto.StudyPiece> studyList = pieceRetriever.findByStudyExamIdAndUserId(userId, exam.getId())
                .stream()
                .map(piece -> ExamResponseDto.StudyPiece.builder()
                        .pieceId(piece.getId())
                        .studyContents(piece.getStudy().getStudyContents())
                        .startPage(piece.getStartPage())
                        .finishPage(piece.getFinishPage())
                        .deadline(piece.getDeadline().toString())
                        .remainingDays((int) ChronoUnit.DAYS.between(piece.getDeadline(), LocalDate.now()))
                        .isFinished(piece.getIsFinished())
                        .build())
                .collect(Collectors.toList());

        int examDday = (int) ChronoUnit.DAYS.between(exam.getExamDate(), LocalDate.now());
        String motivationMessage = exam.getSubject().getMotivationMessage();

        return ExamResponseDto.builder()
                .motivationMessage(motivationMessage)
                .examDday(examDday)
                .examDate(exam.getExamDate().toString())
                .studyList(studyList)
                .build();
    }
}