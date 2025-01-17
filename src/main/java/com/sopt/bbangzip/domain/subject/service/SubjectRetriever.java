package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.repository.UserSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectRetriever {

    private final SubjectRepository subjectRepository;
    private final UserSubjectRepository userSubjectRepository;

    // userSubject subjectName 에 해당하는 과목(Subject) 가 존재하는지 확인하는 메서드
    public boolean existsByUserSubjectAndSubjectName(UserSubject userSubject, String subjectName){
        return subjectRepository.existsByUserSubjectAndSubjectName(userSubject, subjectName);
    }

    // UserSubject 조회
    public final UserSubject findByUserIdAndYearAndSemester(Long userId, int year, String semester) {
        return userSubjectRepository.findByUserIdAndYearAndSemester(userId, year, semester)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER_SUBJECT));
    }

    // 과목 조회 (다건 조회)
    public final List<Subject> findByIdInAndUserSubjectId(List<Long> subjectIds, Long userSubjectId) {
        List<Subject> subjects = subjectRepository.findByIdInAndUserSubjectId(subjectIds, userSubjectId);
        if (subjects.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT);
        }
        return subjects;
    }

    // 과목 조회 (subjectId로 단건조회)
    public Subject findById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT));
    }

    // UserId와 SubjectName으로 특정 과목(Subject) 조회
    public Subject findByUserIdAndSubjectName(Long userId, int year, String semester, String subjectName) {
        // UserSubject 조회
        UserSubject userSubject = findByUserIdAndYearAndSemester(userId, year, semester);

        // Subject 조회
        return subjectRepository.findByUserSubjectAndSubjectName(userSubject, subjectName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT));
    }

    // UserId 과목 ID, 과목 이름으로 과목을 조회하는 메서드
    public Subject findByUserIdAndSubjectName(Long userId, Long subjectId, String subjectName) {
        return subjectRepository.findByUserSubject_UserIdAndIdAndSubjectName(userId, subjectId, subjectName)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT));
    }

}
