package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import com.sopt.bbangzip.domain.study.entity.Study;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto.PieceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PieceSaver {

    private final PieceRepository pieceRepository;

    public List<Piece> savePieces(Study study, List<PieceRequestDto> pieceRequestDtos) {
        List<Piece> pieces = pieceRequestDtos.stream()
                .map(pieceDto -> new Piece(
                        study,
                        pieceDto.startPage(),
                        pieceDto.finishPage(),
                        pieceDto.deadline()
                ))
                .collect(Collectors.toList());
        return pieceRepository.saveAll(pieces);
    }
}
