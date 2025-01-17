package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
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

        public Exam findBySubjectIdAndExamNameAndExamDate(Long subjectId, String examName, LocalDate examDate) {
        return examRepository.findBySubjectIdAndExamNameAndExamDate(subjectId, examName, examDate)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXAM));
    }
}

