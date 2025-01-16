package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.exam.repository.ExamRepository;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ExamSaver {
    private final ExamRepository examRepository;
    private final ExamRetriever examRetriever;

    public Exam saveExam(Subject subject, String examName, LocalDate examDate) {
        try {
            // ExamRetriever를 사용하여 Exam 조회
            return examRetriever.findBySubjectIdAndExamNameAndExamDate(subject.getId(), examName, examDate);
        } catch (Exception e) {
            // Exam이 없을 경우 새로 생성 후 저장
            return examRepository.save(new Exam(subject, examName, examDate));
        }
    }
}

