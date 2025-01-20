package com.sopt.bbangzip.domain.study.service;

import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeResponse;
import com.sopt.bbangzip.domain.badge.service.BadgeService;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.service.ExamSaver;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.service.PieceSaver;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto;

import com.sopt.bbangzip.domain.study.api.dto.response.CreateStudyResponse;
import com.sopt.bbangzip.domain.study.entity.Study;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.service.SubjectRetriever;
import com.sopt.bbangzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudyService {
    private final PieceSaver pieceSaver;
    private final ExamSaver examSaver;
    private final StudySaver studySaver;
    private final SubjectRetriever subjectRetriever;
    private final BadgeService badgeService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public CreateStudyResponse createStudy(Long userId, StudyCreateRequestDto studyCreateRequestDto) {
        Subject subject = subjectRetriever.findByUserIdAndSubjectId(userId, studyCreateRequestDto.subjectId());

        LocalDate examDate;
        try {
            examDate = LocalDate.parse(studyCreateRequestDto.examDate(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
        }

        Exam exam = Exam.builder()
                .examName(studyCreateRequestDto.examName())
                .examDate(examDate)
                .subject(subject)
                .build();
        examSaver.save(exam);

        Study study = Study.builder()
                .exam(exam)
                .studyContents(studyCreateRequestDto.studyContents())
                .build();
        studySaver.save(study);

        List<Piece> pieces = studyCreateRequestDto.pieceList().stream()
                .map(pieceDto -> {
                    LocalDate deadline = LocalDate.parse(pieceDto.deadline(), DATE_FORMATTER);
                    return Piece.builder()
                            .study(study)
                            .startPage(pieceDto.startPage())
                            .finishPage(pieceDto.finishPage())
                            .deadline(deadline)
                            .build();
                })
                .collect(Collectors.toList());
        pieceSaver.saveAll(pieces);

        // 조건 충족 시 뱃지 부여
        User user = subject.getUserSubject().getUser();
        List<BadgeResponse> badges = badgeService.getAllEligibleBadges(user);

        return CreateStudyResponse.builder()
                .badges(badges)
                .build();
    }
}