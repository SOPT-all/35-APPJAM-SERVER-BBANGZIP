package com.sopt.bbangzip.domain.piece.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;

import com.sopt.bbangzip.domain.piece.api.dto.request.IsFinishedDto;
import com.sopt.bbangzip.domain.piece.api.dto.response.MarkDoneResponse;

import com.sopt.bbangzip.domain.piece.api.dto.PieceDeleteRequestDto;

import com.sopt.bbangzip.domain.piece.service.PieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PieceController {

    private final PieceService pieceService;

    // 공부 조각 완료 체크하기 API
    @PostMapping("/pieces/{pieceId}/mark-done")
    public ResponseEntity<MarkDoneResponse> markDone(
            @UserId final long userId,
            @PathVariable final long pieceId,
            @RequestBody @Valid IsFinishedDto isFinishedDto
    )
    {
        return ResponseEntity.ok(pieceService.updateStatus(userId, pieceId, isFinishedDto));
    @DeleteMapping("/studies/pieces")
    public ResponseEntity<Void> deletePieces(
            @UserId final Long userId,
            @RequestBody @Valid final PieceDeleteRequestDto pieceDeleteRequestDto
    ) {
        pieceService.deletePieces(pieceDeleteRequestDto);
        return ResponseEntity.noContent().build();
    }
}
