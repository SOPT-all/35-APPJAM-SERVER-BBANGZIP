package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.study.entity.Study;
import com.sopt.bbangzip.domain.study.api.dto.request.StudyCreateRequestDto.PieceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PieceService {

    private final PieceSaver pieceSaver;

    // Piece 생성 및 저장
    public List<Piece> createAndSavePieces(Study study, List<PieceRequestDto> pieceRequestDtos) {
        List<Piece> pieces = pieceRequestDtos.stream()
                .map(pieceDto -> Piece.builder()
                        .study(study)
                        .startPage(pieceDto.startPage())
                        .finishPage(pieceDto.finishPage())
                        .deadline(pieceDto.deadline())
                        .build()
                )
                .collect(Collectors.toList());
        return pieceSaver.saveAll(pieces);
    }
}
