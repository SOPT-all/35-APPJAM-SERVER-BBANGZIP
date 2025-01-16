package com.sopt.bbangzip.domain.exam.entity;

import com.sopt.bbangzip.common.constants.entity.ExamTableConstants;
import com.sopt.bbangzip.domain.subject.entity.Subject;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = ExamTableConstants.TABLE_EXAM)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ExamTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ExamTableConstants.COLUMN_SUBJECT_ID, nullable = false)
    private Subject subject;

    @Column(name = ExamTableConstants.COLUMN_EXAM_NAME, nullable = false)
    private String examName;

    @Column(name = ExamTableConstants.COLUMN_EXAM_DATE)
    private LocalDate examDate;

    @Column(name = ExamTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Exam(Subject subject, String examName, LocalDate examDate) {
        this.subject = subject;
        this.examName = examName;
        this.examDate = examDate;
        this.createdAt = LocalDateTime.now();
    }

}
