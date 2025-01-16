package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.piece.api.dto.PieceDeleteRequestDto;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieceService {

    private final PieceRepository pieceRepository;
    private final PieceRetriever pieceRetriever;

    @Transactional
    public void deletePieces(PieceDeleteRequestDto pieceDeleteRequestDto) {
        List<Long> pieceIds = pieceDeleteRequestDto.pieceIds();
        List<Piece> pieces = pieceRetriever.findAllByIds(pieceIds);
        pieceRepository.deleteAll(pieces);
    }

}
