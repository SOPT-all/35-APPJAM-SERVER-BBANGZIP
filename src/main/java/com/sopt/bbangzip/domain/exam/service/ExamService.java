package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.common.exception.base.InvalidOptionsException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.exam.api.dto.response.ExamResponseDto;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
import com.sopt.bbangzip.domain.piece.service.PieceRetriever;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.service.SubjectRetriever;
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

    private final ExamRepository examRepository;
    private final PieceRetriever pieceRetriever;

    private final SubjectRetriever subjectRetriever;

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

        // 과목 조회
        Subject subject = subjectRetriever.findByIdAndUserId(userId, subjectId);

        // 시험 조회하는데 없으면 만들기
        Exam exam = examRepository.findBySubjectIdAndExamNameAndUser(userId, convertedExamName, subjectId)
                .orElseGet(() -> {
                    createDefaultExams(subject);
                    return examRepository.findBySubjectIdAndExamNameAndUser(userId, convertedExamName, subjectId)
                            .orElseThrow(() -> new IllegalStateException("기본 시험 생성에 실패했습니다"));
                });

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

        // 시험 날짜가 없을 경우 D-Day는 반환하지 않음
        Integer examDday = exam.getExamDate() != null ? (int) ChronoUnit.DAYS.between(exam.getExamDate(), LocalDate.now()) : null;
        String motivationMessage = exam.getSubject().getMotivationMessage();

        return ExamResponseDto.builder()
                .subjectName(subject.getSubjectName())
                .motivationMessage(motivationMessage)
                .examDday(examDday != null ? examDday : -999) // 기본값 -1 설정
                .examDate(exam.getExamDate() != null ? exam.getExamDate().toString() : "Not Scheduled")
                .studyList(studyList)
                .build();
    }

    public void createDefaultExams(final Subject subject) {
        Exam midExam = Exam.builder()
                .examName("중간고사")
                .examDate(null)
                .subject(subject)
                .build();

        Exam finalExam = Exam.builder()
                .examName("기말고사")
                .examDate(null)
                .subject(subject)
                .build();

        examRepository.saveAll(List.of(midExam, finalExam));
        log.info("기본 시험 생성: 중간고사, 기말고사 for subjectId={}", subject.getId());
    }
}