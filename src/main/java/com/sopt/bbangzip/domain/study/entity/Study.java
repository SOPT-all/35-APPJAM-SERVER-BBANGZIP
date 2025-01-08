package com.sopt.bbangzip.domain.study.entity;

import com.sopt.bbangzip.common.constants.entity.StudyTableConstants;
import com.sopt.bbangzip.domain.exam.entity.Exam;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = StudyTableConstants.TABLE_STUDY)
public class Study {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = StudyTableConstants.COLUMN_ID)
    private Long id;

    @Column(name = StudyTableConstants.COLUMN_STUDY_CONTENTS)
    private String studyContents;

    @Column(name = StudyTableConstants.COLUMN_START_PAGE)
    private Integer startPage;

    @Column(name = StudyTableConstants.COLUMN_FINISH_PAGE)
    private Integer finishPage;

    @Column(name = StudyTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = StudyTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = StudyTableConstants.COLUMN_EXAM_ID, nullable = false)
    private Exam exam;

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<Piece> pieces;

}
