package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ExamRetriever {
    private final ExamRepository examRepository;

        public Exam findBySubjectIdAndExamNameAndUser(final Long userId, final String examName, final Long subjectId) {
        return examRepository.findBySubjectIdAndExamNameAndUser(userId,examName, subjectId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXAM));
    }

    public Optional<Exam> findByExamNameAndDateAndSubject(String examName, LocalDate examDate, Subject subject) {
        return examRepository.findByExamNameAndExamDateAndSubject(examName, examDate, subject);
    }

}

