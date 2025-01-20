package com.sopt.bbangzip.domain.piece.entity;

import com.sopt.bbangzip.common.constants.entity.PieceTableConstants;
import com.sopt.bbangzip.domain.study.entity.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = PieceTableConstants.TABLE_PIECE)
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PieceTableConstants.COLUMN_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PieceTableConstants.COLUMN_STUDY_ID, nullable = false)
    private Study study;

    @Column(name = PieceTableConstants.COLUMN_PIECE_NUMBER, nullable = false)
    private int pieceNumber;

    @Column(name = PieceTableConstants.COLUMN_START_PAGE, nullable = false)
    private int startPage;

    @Column(name = PieceTableConstants.COLUMN_FINISH_PAGE, nullable = false)
    private int finishPage;

    @Column(name = PieceTableConstants.COLUMN_DEADLINE)
    private LocalDate deadline;

    @Column(name = PieceTableConstants.COLUMN_IS_FINISHED, nullable = false)
    private Boolean isFinished = false;

    @Column(name = PieceTableConstants.COLUMN_IS_VISIBLE, nullable = false)
    private Boolean isVisible = false;

    @Column(name = PieceTableConstants.COLUMN_PAGE_AMOUNT)
    private int pageAmount;

    @Column(name = PieceTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = PieceTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;


    public void updateStatus(Boolean isFinished) {
        this.isFinished = isFinished;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Piece(Study study, int startPage, int finishPage, LocalDate deadline) {
        this.study = study;
        this.startPage = startPage;
        this.finishPage = finishPage;
        this.pageAmount = finishPage - startPage + 1;
        this.deadline = deadline;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
