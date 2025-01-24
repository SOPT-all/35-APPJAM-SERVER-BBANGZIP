package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.DuplicateSubjectException;
import com.sopt.bbangzip.common.exception.base.InvalidOptionsException;
import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.piece.service.PieceRetriever;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectDeleteDto;
import com.sopt.bbangzip.domain.subject.api.dto.response.SubjectResponseDto;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.service.UserSubjectRetriever;
import com.sopt.bbangzip.domain.userSubject.service.UserSubjectSaver;
import jakarta.transaction.Transactional;
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
public class SubjectService {

    private final UserRetriever userRetriever;

    private final SubjectRetriever subjectRetriever;
    private final PieceRetriever pieceRetriever;
    private final SubjectSaver subjectSaver;
    private final SubjectRemover subjectRemover;

    private final UserSubjectRetriever userSubjectRetriever;
    private final UserSubjectSaver userSubjectSaver;
    private final SubjectUpdater subjectUpdater;

    public void createSubject(
            final Long userId,
            final SubjectCreateDto subjectCreateDto
    ) {
        User user = userRetriever.findByUserId(userId); // 유저 존재 여부까지 확인 가능

        // UserSubject 조회 또는 생성
        UserSubject userSubject = userSubjectRetriever.findByUserIdAndYearAndSemester(userId, subjectCreateDto.year(), subjectCreateDto.semester());
        userSubjectSaver.save(userSubject);

        // 동일 학기 내 중복된 과목 존재 여부 확인
        boolean isDuplicate = subjectRetriever.existsByUserSubjectAndSubjectNameAndUserId(
                userId,
                userSubject.getId(),
                subjectCreateDto.subjectName()
        );

        if (isDuplicate) {
            throw new DuplicateSubjectException(ErrorCode.DUPLICATED_SUBJECT);
        }

        // 중복된 과목명 없으면 subject 만들고
        Subject subject = Subject.builder()
                .userSubject(userSubject)
                .subjectName(subjectCreateDto.subjectName())
                .build();

        // subject 자체를 넘겨줘서 저장하기
        subjectSaver.save(subject);
    }

    public void deleteSubject(
            final Long userId,
            final SubjectDeleteDto subjectDeleteDto
    ) {
        // 유저 검증
        userRetriever.findByUserId(userId);

        // 주어진 연도와 학기에 대한 UserSubject 조회
        UserSubject userSubject = subjectRetriever.findByUserIdAndYearAndSemester(userId, subjectDeleteDto.year(), subjectDeleteDto.semester());

        if (userSubject == null) {throw new NotFoundException(ErrorCode.NOT_FOUND_USER_SUBJECT); }

        // 삭제할 과목 조회
        List<Subject> subjects = subjectRetriever.findByIdInAndUserSubjectIdAndUserId(userId, subjectDeleteDto.subjectIds(), userSubject.getId());

        if (subjects.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT);
        }

        // SubjectRemover를 통해 삭제 처리
        subjectRemover.removeSubjects(subjects);
    }

    @Transactional
    public void updateSubjectNameOrMotivationMessage(
            final Long userId,
            final Long subjectId,
            final String options,
            final String value
    ){
        userRetriever.findByUserId(userId);
        Subject subject = subjectRetriever.findByIdAndUserId(userId, subjectId);
        switch (options) {
            case "subjectName" -> subjectUpdater.updateSubjectName(subject, value);
            case "motivationMessage" -> subjectUpdater.updateMotivationMessage(subject, value);
            default -> throw new InvalidOptionsException(ErrorCode.INVALID_OPTION);
        }
    }

    /**
     * 사용자와 학기 정보에 따른 과목 조회
     * @return SubjectResponseDto - 사용자와 학기에 해당하는 과목 정보와 시험별 공부 현황(남은 공부, 밀린 공부)을 포함한 응답 객체
     */
    public SubjectResponseDto getSubjectsByUserAndSemester(
            final Long userId,
            final int year,
            final String semester
    ){
        // 사용자 검증
        userRetriever.findByUserId(userId);

        // 학기별 과목 조회
        List<Subject> subjects = subjectRetriever.findSubjectsByUserAndSemester(userId, year, semester);

        // Subject 리스트 생성
        List<SubjectResponseDto.SubjectDto> subjectList = subjects.stream()
                .map(subject -> new SubjectResponseDto.SubjectDto(
                        subject.getId(),
                        subject.getSubjectName(),
                        subject.getExams().stream()
                                .map(exam -> mapExamToStudyDto(userId, subject.getId(), exam)) // subjectId 전달
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return new SubjectResponseDto(year, semester, subjectList);
    }

    /**
     * Exam 객체를 StudyDto로 매핑
     * @return StudyDto - 시험 이름, D-Day, 밀린 공부 개수, 남은 공부 개수를 포함한 DTO
     */
    private SubjectResponseDto.SubjectDto.StudyDto mapExamToStudyDto(
            final Long userId,
            final Long subjectId,
            final Exam exam
    ){
        int dDay = (int) ChronoUnit.DAYS.between(exam.getExamDate(), LocalDate.now());

        // 시험별 남은 공부와 밀린 공부 조회
        int remainingCount = pieceRetriever.findRemainingCount(userId, subjectId, exam.getId());
        int pendingCount = pieceRetriever.findPendingCount(userId, subjectId, exam.getId());

        return new SubjectResponseDto.SubjectDto.StudyDto(
                exam.getExamName(),
                dDay,
                pendingCount,
                remainingCount
        );
    }
}