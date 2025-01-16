package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PieceRetriever {

    private final PieceRepository pieceRepository;

    public Piece findByPieceIdAndUserId(final long pieceId, final long userId){
        return pieceRepository.findByPieceIdAndUserId(pieceId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PIECE));
    }

    public int countUnfinishedTodayPieces(final Long userId){
        return pieceRepository.countUnfinishedTodayPieces(userId);
    }

    public int countFinishedTodayPieces(final Long userId) {
        return pieceRepository.countFinishedTodayPieces(userId);
    }
}