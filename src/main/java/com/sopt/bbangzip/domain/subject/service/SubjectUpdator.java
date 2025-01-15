package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.InvalidOptionsException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectUpdator {

    private final SubjectRepository subjectRepository;

    public void updateSubjectField(Subject subject, String options, String value) {

        switch (options) {
            case "subjectName" -> subject.updateSubjectName(value);
            case "motivationMessage" -> subject.updateMotivationMessage(value);
            default -> throw new InvalidOptionsException(ErrorCode.INVALID_OPTION);
        }

        subjectRepository.save(subject);
    }
}
