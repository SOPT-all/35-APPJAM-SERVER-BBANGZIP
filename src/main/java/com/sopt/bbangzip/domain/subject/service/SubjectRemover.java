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
    private final SubjectRepository subjectRepository;

    public final void removeSubjects(List<Subject> subjects) {
        subjectRepository.deleteAll(subjects);
    }
}

