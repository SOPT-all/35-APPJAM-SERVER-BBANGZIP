package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExamSaver {
    private final ExamRepository examRepository;

    // 시험 저장
    @Transactional
    public void save(final Exam exam) {
        examRepository.save(exam);
    }

    // Exam 객체 생성 및 저장
    @Transactional
    public Exam createAndSaveExam(Subject subject, String examName, LocalDate examDate) {
        Exam newExam = Exam.builder()
                .subject(subject)
                .examName(examName)
                .examDate(examDate)
                .build();
        return examRepository.save(newExam);
    }
}

