package com.sopt.bbangzip.domain.study.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.service.ExamRetriever;
import com.sopt.bbangzip.domain.exam.service.ExamSaver;
import com.sopt.bbangzip.domain.exam.service.ExamService;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.service.PieceSaver;
import com.sopt.bbangzip.domain.piece.service.PieceService;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto;
import com.sopt.bbangzip.domain.study.api.dto.response.StudyCreateResponseDto;
import com.sopt.bbangzip.domain.study.entity.Study;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.service.SubjectRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final PieceSaver pieceSaver;
    private final ExamRetriever examRetriever;
    private final ExamSaver examSaver;
    private final StudySaver studySaver;
    private final SubjectRetriever subjectRetriever;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년MM월dd일");

    @Transactional
    public StudyCreateResponseDto createStudy(Long userId, StudyCreateRequestDto requestDto) {

        Subject subject = subjectRetriever.findByUserIdAndSubjectName(userId, requestDto.subjectId(), requestDto.subjectName());
        Exam exam = examRetriever.findBySubjectIdAndExamNameAndExamDate(subject.getId(), requestDto.examName(), parseStringToLocalDate(requestDto.examDate()));
        examSaver.save(exam);

        Study study = Study.builder()
                .exam(exam)
                .studyContents(requestDto.studyContents())
                .build();
        studySaver.save(study);

        List<Piece> pieces = requestDto.pieceList().stream()
                .map(pieceDto -> Piece.builder()
                        .study(study)
                        .startPage(pieceDto.startPage())
                        .finishPage(pieceDto.finishPage())
                        .deadline(pieceDto.deadline())
                        .build()
                )
                .collect(Collectors.toList());
        pieceSaver.saveAll(pieces);

        return StudyCreateResponseDto.builder()
                .studyId(study.getId())
                .examId(exam.getId())
                .studyContents(study.getStudyContents())
                .startPage(pieces.stream().mapToInt(Piece::getStartPage).min().orElse(0))
                .finishPage(pieces.stream().mapToInt(Piece::getFinishPage).max().orElse(0))
                .examDate(exam.getExamDate().format(DATE_FORMATTER))
                .build();
    }

    // 날짜 String을 LocalDateTime으로 변환
    private LocalDate parseStringToLocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}
