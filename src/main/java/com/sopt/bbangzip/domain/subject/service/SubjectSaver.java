package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectSaver {

    private final SubjectRepository subjectRepository;

    // 과목 저장
    public void save(final Subject subject) {
        subjectRepository.save(subject);
    }
}
