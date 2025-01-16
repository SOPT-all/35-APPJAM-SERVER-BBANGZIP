package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ExamSaver {
    private final ExamRepository examRepository;

    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }
}

