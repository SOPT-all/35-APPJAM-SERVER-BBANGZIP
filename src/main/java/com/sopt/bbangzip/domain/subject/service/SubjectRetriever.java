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

    /**
     * UserSubject와 SubjectName으로 Subject 존재 여부 확인
     *
     * @param userSubjectId UserSubject ID
     * @param userId        사용자 ID
     * @param subjectName   과목 이름
     * @return Subject 존재 여부
     */
    public boolean existsByUserSubjectAndSubjectNameAndUserId(Long userId, Long userSubjectId, String subjectName) {
        return subjectRepository.existsByUserSubjectAndSubjectNameAndUserId(userId, userSubjectId, subjectName);
    }

    // UserSubject 조회
    public final UserSubject findByUserIdAndYearAndSemester(Long userId, int year, String semester) {
        return userSubjectRepository.findByUserIdAndYearAndSemester(userId, year, semester)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER_SUBJECT));
    }

    /**
     * 특정 UserSubject ID와 과목 ID, 그리고 User ID를 기준으로 과목 조회
     *
     * @param subjectIds    조회할 과목 ID 리스트
     * @param userSubjectId UserSubject ID
     * @param userId        사용자 ID
     * @return 조회된 과목 리스트
     */
    public List<Subject> findByIdInAndUserSubjectIdAndUserId(Long userId, List<Long> subjectIds, Long userSubjectId) {
        List<Subject> subjects = subjectRepository.findByIdInAndUserSubjectIdAndUserId(userId, subjectIds, userSubjectId);
        if (subjects.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT);
        }
        return subjects;
    }

    public Subject findByIdAndUserId(Long userId, Long subjectId) {
        return subjectRepository.findByIdAndUserId(userId, subjectId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SUBJECT));
    }

    /**
     * 사용자와 학기 정보에 따른 과목 조회
     *
     * @param userId 사용자 ID
     * @param year 학년
     * @param semester 학기
     * @return 과목 목록
     */
    public List<Subject> findSubjectsByUserAndSemester(Long userId, int year, String semester) {
        return subjectRepository.findSubjectsByUserAndSemester(userId, year, semester);
    }
}
