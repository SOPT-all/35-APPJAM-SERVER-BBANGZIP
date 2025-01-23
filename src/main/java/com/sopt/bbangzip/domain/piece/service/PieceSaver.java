package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PieceSaver {

    private final PieceRepository pieceRepository;

    public List<Piece> saveAll(final List<Piece> pieces) {
        return pieceRepository.saveAll(pieces);
    }
}
