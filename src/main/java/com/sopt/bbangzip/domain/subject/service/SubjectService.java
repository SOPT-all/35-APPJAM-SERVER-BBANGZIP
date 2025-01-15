package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.DuplicateSubjectException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectDeleteDto;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.service.UserSubjectRetriever;
import com.sopt.bbangzip.domain.userSubject.service.UserSubjectSaver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final UserRetriever userRetriever;

    private final SubjectRetriever subjectRetriever;
    private final SubjectSaver subjectSaver;
    private final SubjectRemover subjectRemover;

    private final UserSubjectRetriever userSubjectRetriever;
    private final UserSubjectSaver userSubjectSaver;

    public void createSubject(Long userId, SubjectCreateDto subjectCreateDto) {

        User user = userRetriever.findByUserId(userId); // 유저 존재 여부까지 확인 가능

        // UserSubject 조회 또는 생성
        UserSubject userSubject = userSubjectRetriever.findByUserIdAndYearAndSemester(userId, subjectCreateDto.year(), subjectCreateDto.semester());
        userSubjectSaver.save(userSubject);

        // 동일 학기 내 중복된 과목 있으면 예외 던지기
        if (subjectRetriever.existsByUserSubjectAndSubjectName(userSubject, subjectCreateDto.subjectName())) {
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

    public void deleteSubject(Long userId, SubjectDeleteDto subjectDeleteDto) {
        // 유저 검증
        userRetriever.findByUserId(userId);

        // 주어진 연도와 학기에 대한 UserSubject 조회
        UserSubject userSubject = subjectRetriever.findByUserIdAndYearAndSemester(userId, subjectDeleteDto.year(), subjectDeleteDto.semester());

        // 삭제할 과목 조회
        List<Subject> subjects = subjectRetriever.findByIdInAndUserSubjectId(subjectDeleteDto.subjectIds(), userSubject.getId());

        // SubjectRemover를 통해 삭제 처리
        subjectRemover.removeSubjects(subjects);
    }
}