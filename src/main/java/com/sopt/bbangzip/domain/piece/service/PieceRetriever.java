package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PieceRetriever {
    private final PieceRepository pieceRepository;

    // 특정 ID 리스트에 해당하는 조각(Piece)들을 조회하는 메서드
    public List<Piece> findAllByIds(List<Long> pieceIds) {
        List<Piece> pieces = pieceRepository.findAllById(pieceIds);

        // 유효한 조각인지 검증
        if (pieces.isEmpty() || pieces.size() != pieceIds.size()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_PIECE);
        }
        return pieces;
    }
}
