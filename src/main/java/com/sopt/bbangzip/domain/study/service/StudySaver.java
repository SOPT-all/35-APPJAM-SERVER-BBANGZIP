package com.sopt.bbangzip.domain.study.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.study.entity.Study;
import com.sopt.bbangzip.domain.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudySaver {

    private final StudyRepository studyRepository;

    public Study saveStudy(Exam exam, String studyContents) {
        Study study = new Study(
                exam,
                studyContents
        );
        return studyRepository.save(study);
    }
}
