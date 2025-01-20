package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.exam.api.dto.response.ExamResponseDto;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import com.sopt.bbangzip.domain.piece.service.PieceRetriever;
import com.sopt.bbangzip.domain.subject.entity.Subject;
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
     * @return ExamResponseDto
     */
    public ExamResponseDto getExamInfoWithConversion(Long userId, String examName, Long subjectId) {
        String convertedExamName = convertExamName(examName);
        if (convertedExamName == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_EXAM);
        }
        return getExamInfo(userId, convertedExamName, subjectId);
    }

    /**
     * 특정 시험 정보 및 관련 공부 조각 리스트
     *
     * @param subjectId 과목 ID
     * @param examName  시험 이름
     * @return ExamResponseDto
     */
    public ExamResponseDto getExamInfo(Long userId, String examName, Long subjectId) {
        Exam exam = examRetriever.findBySubjectIdAndExamNameAndUser(userId, examName, subjectId);

        int examDday = calculateDday(exam.getExamDate());

        List<ExamResponseDto.StudyPiece> studyList = getStudyPieces(userId, exam);

        String motivationMessage = exam.getSubject().getMotivationMessage();

        return ExamResponseDto.builder()
                .motivationMessage(motivationMessage)
                .examDday(examDday)
                .examDate(exam.getExamDate().toString())
                .studyList(studyList)
                .build();
    }

    /**
     * "mid"/"fin"을 "중간고사"/"기말고사"로 변환
     *
     * @param examName 시험 이름 ("mid" 또는 "fin")
     * @return 변환된 이름 ("중간고사" 또는 "기말고사")
     */
    private String convertExamName(String examName) {
        switch (examName) {
            case "mid":
                return "중간고사";
            case "fin":
                return "기말고사";
            default:
                return null;
        }
    }

    /**
     * 시험 D-Day 계산
     *
     * @param examDate 시험 날짜
     * @return D-Day 값
     */
    private int calculateDday(LocalDate examDate) {
        int dday = (int) ChronoUnit.DAYS.between(examDate, LocalDate.now());
        return dday;
    }

    /**
     * 시험과 연관된 공부 조각 리스트를 조회하고 변환
     *
     * @param exam Exam 엔티티
     * @return List<ExamResponseDto.StudyPiece>
     */
    private List<ExamResponseDto.StudyPiece> getStudyPieces(Long userId, Exam exam) {
        List<Piece> pieces = pieceRetriever.findByStudyExamIdAndUserId(userId, exam.getId());

        return pieces.stream()
                .map(this::convertToStudyPiece)
                .collect(Collectors.toList());
    }

    /**
     * Piece 엔티티를 StudyPiece DTO로 변환
     *
     * @param piece Piece 엔티티
     * @return ExamResponseDto.StudyPiece
     */
    private ExamResponseDto.StudyPiece convertToStudyPiece(Piece piece) {
        return ExamResponseDto.StudyPiece.builder()
                .pieceId(piece.getId())
                .studyContents(piece.getStudy().getStudyContents())
                .startPage(piece.getStartPage())
                .finishPage(piece.getFinishPage())
                .deadline(piece.getDeadline().toString())
                .remainingDays(calculateRemainingDays(piece.getDeadline()))
                .isFinished(piece.getIsFinished())
                .build();
    }

    //남은 일수 계산
    private int calculateRemainingDays(LocalDate deadline) {
        return (int) ChronoUnit.DAYS.between(deadline, LocalDate.now());
    }
}
