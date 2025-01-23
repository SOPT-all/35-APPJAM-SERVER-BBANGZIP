package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PieceRemover {

    private final PieceRepository pieceRepository;

    public void removePieces(final List<Piece> pieces) {
        pieceRepository.deleteAll(pieces);
    }
}
