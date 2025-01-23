package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.InvalidOptionsException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectUpdater {
    public void updateSubjectName(final Subject subject, final String newName) {
        subject.updateSubjectName(newName);
    }

    public void updateMotivationMessage(final Subject subject, final String newMessage) {
        subject.updateMotivationMessage(newMessage);
    }
}
