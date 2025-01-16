package com.sopt.bbangzip.domain.study.entity;

import com.sopt.bbangzip.common.constants.entity.StudyTableConstants;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = StudyTableConstants.TABLE_STUDY)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = StudyTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = StudyTableConstants.COLUMN_EXAM_ID, nullable = false)
    private Exam exam;

    @Column(name = StudyTableConstants.COLUMN_STUDY_CONTENTS)
    private String studyContents;

    @Column(name = StudyTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Piece> pieces;

    @Builder
    public Study(Exam exam, String studyContents) {
        this.exam = exam;
        this.studyContents = studyContents;
        this.createdAt = LocalDateTime.now();
    }
}
