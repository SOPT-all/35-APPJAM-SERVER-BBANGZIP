package com.sopt.bbangzip.domain.piece.entity;

import com.sopt.bbangzip.common.constants.entity.PieceTableConstants;
import com.sopt.bbangzip.domain.study.entity.Study;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = PieceTableConstants.TABLE_PIECE)
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PieceTableConstants.COLUMN_ID)
    private Long id;

    @Column(name = PieceTableConstants.COLUMN_PIECE_NUMBER, nullable = false)
    private Long pieceNumber;

    @Column(name = PieceTableConstants.COLUMN_START_PAGE, nullable = false)
    private Long startPage;

    @Column(name = PieceTableConstants.COLUMN_FINISH_PAGE, nullable = false)
    private Long finishPage;

    @Column(name = PieceTableConstants.COLUMN_DEADLINE)
    private LocalDateTime deadline;

    @Column(name = PieceTableConstants.COLUMN_IS_FINISHED, nullable = false)
    private Boolean isFinished;

    @Column(name = PieceTableConstants.COLUMN_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = PieceTableConstants.COLUMN_UPDATED_AT)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PieceTableConstants.COLUMN_STUDY_ID, nullable = false)
    private Study study;
}
