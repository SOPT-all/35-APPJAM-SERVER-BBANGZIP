package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.DuplicateSubjectException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectCreateDto;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectSaver {

    private final SubjectRepository subjectRepository;

    // 중복된 과목명 여부 확인
    public void validateDuplicateSubject(UserSubject userSubject, String subjectName) {
        if (subjectRepository.existsByUserSubjectAndSubjectName(userSubject, subjectName)) {
            throw new DuplicateSubjectException(ErrorCode.NOT_FOUND_SUBJECT);
        }
    }

    // 과목 저장
    public void saveSubject(UserSubject userSubject, SubjectCreateDto subjectCreateDto) {
        Subject subject = Subject.builder()
                .userSubject(userSubject)
                .subjectName(subjectCreateDto.subjectName())
                .build();

        subjectRepository.save(subject);
    }
}
