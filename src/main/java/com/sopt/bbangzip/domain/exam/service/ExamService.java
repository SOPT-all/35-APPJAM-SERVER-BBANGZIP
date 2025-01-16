package com.sopt.bbangzip.domain.exam.service;

import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamService {
    private final ExamRetriever examRetriever;
    private final ExamSaver examSaver;

    public Exam findOrCreateExam(Subject subject, String examName, LocalDate examDate) {
        return examRetriever.findBySubjectIdAndExamNameAndExamDate(subject.getId(), examName, examDate)
                .orElseGet(() -> examSaver.createAndSaveExam(subject, examName, examDate));
    }
}

