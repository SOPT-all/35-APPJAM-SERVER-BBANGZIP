package com.sopt.bbangzip.domain.piece.entity;

import com.sopt.bbangzip.common.constants.entity.PieceTableConstants;
import com.sopt.bbangzip.domain.study.entity.Study;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
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
    private Integer pieceNumber;

    @Column(name = PieceTableConstants.COLUMN_START_PAGE, nullable = false)
    private Integer startPage;

    @Column(name = PieceTableConstants.COLUMN_FINISH_PAGE, nullable = false)
    private Integer finishPage;

    @Column(name = PieceTableConstants.COLUMN_DEADLINE)
    private LocalDateTime deadline;

    @Column(name = PieceTableConstants.COLUMN_IS_FINISHED, nullable = false)
    private Boolean isFinished;

    @Column(name = PieceTableConstants.COLUMN_IS_VISIBLE, nullable = false)
    private Boolean isVisible;

    @Column(name = PieceTableConstants.COLUMN_PAGE_AMOUNT)
    private Integer pageAmount;

    @Column(name = PieceTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = PieceTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

}
