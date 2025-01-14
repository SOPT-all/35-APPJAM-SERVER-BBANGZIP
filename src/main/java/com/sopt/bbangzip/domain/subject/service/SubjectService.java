package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRetriever subjectRetriever;
    private final SubjectSaver subjectSaver;

    public void createSubject(Long userId, SubjectCreateDto subjectCreateDto) {
        // 1. 유저 존재 확인
        subjectRetriever.validateUserExists(userId);

        // 2. UserSubject 조회 또는 생성
        UserSubject userSubject = subjectRetriever.findOrCreateUserSubject(
                userId, subjectCreateDto.year(), subjectCreateDto.semester()
        );

        // 3. 동일 학기 내 중복된 과목명 확인
        subjectSaver.validateDuplicateSubject(userSubject, subjectCreateDto.subjectName());

        // 4. 과목 저장
        subjectSaver.saveSubject(userSubject, subjectCreateDto);
    }

}