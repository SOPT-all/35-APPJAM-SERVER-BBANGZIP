package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.api.dto.request.SubjectDeleteDto;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.repository.UserSubjectRepository;
import com.sopt.bbangzip.domain.userSubject.service.UserSubjectRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
@RequiredArgsConstructor
public class SubjectRemover {


    private final SubjectRetriever subjectRetriever;
    private final SubjectRepository subjectRepository;

    private final UserSubjectRetriever userSubjectRetriever;
    private final UserSubjectRepository userSubjectRepository;

    @Transactional
    public void removeSubjects(Long userId, SubjectDeleteDto subjectDeleteDto) {
        // 주어진 연도와 학기에 대한 UserSubject 조회
        UserSubject userSubject = subjectRetriever.findByUserIdAndYearAndSemester(userId, subjectDeleteDto.year(), subjectDeleteDto.semester());

        // 삭제할 과목 조회
        List<Subject> subjects = subjectRetriever.findByIdInAndUserSubjectId(subjectDeleteDto.subjectIds(), userSubject.getId());

        // 과목 삭제
        subjectRepository.deleteAll(subjects);
    }
}

