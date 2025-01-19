package com.sopt.bbangzip.domain.study.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.service.ExamSaver;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.service.PieceSaver;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto;

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
    private final ExamSaver examSaver;
    private final StudySaver studySaver;
    private final SubjectRetriever subjectRetriever;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public void createStudy(Long userId, StudyCreateRequestDto studyCreateRequestDto) {
        Subject subject = subjectRetriever.findById(studyCreateRequestDto.subjectId());

        Exam exam = Exam.builder()
                .examName(studyCreateRequestDto.examName())
                .examDate(parseStringToLocalDate(studyCreateRequestDto.examDate()))
                .subject(subject)
                .build();
        examSaver.save(exam);

        Study study = Study.builder()
                .exam(exam)
                .studyContents(studyCreateRequestDto.studyContents())
                .build();
        studySaver.save(study);

        List<Piece> pieces = studyCreateRequestDto.pieceList().stream()
                .map(pieceDto -> Piece.builder()
                        .study(study)
                        .startPage(pieceDto.startPage())
                        .finishPage(pieceDto.finishPage())
                        .deadline(pieceDto.deadline())
                        .build()
                )
                .collect(Collectors.toList());

        pieceSaver.saveAll(pieces);
    }

    // 날짜 String을 LocalDate로 변환
    private LocalDate parseStringToLocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}