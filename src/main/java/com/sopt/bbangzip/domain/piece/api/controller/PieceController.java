package com.sopt.bbangzip.domain.piece.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;

import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.domain.piece.api.dto.request.IsFinishedDto;
import com.sopt.bbangzip.domain.piece.api.dto.request.PieceAddRequestDto;
import com.sopt.bbangzip.domain.piece.api.dto.response.AddTodoPiecesResponse;
import com.sopt.bbangzip.domain.piece.api.dto.response.MarkDoneResponse;

import com.sopt.bbangzip.domain.piece.api.dto.request.PieceDeleteRequestDto;

import com.sopt.bbangzip.domain.piece.api.dto.response.TodoPiecesResponse;
import com.sopt.bbangzip.domain.piece.service.PieceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @RequestBody @Valid final IsFinishedDto isFinishedDto
    ) {
        return ResponseEntity.ok(pieceService.markDone(userId, pieceId, isFinishedDto));
    }

    // 공부 조각 미완료 체크하기 API
    @PostMapping("/pieces/{pieceId}/mark-undone")
    public ResponseEntity<ResponseDto<List<Object>>>  markUnDone(
            @UserId final long userId,
            @PathVariable final long pieceId,
            @RequestBody @Valid final IsFinishedDto isFinishedDto
    ) {
        pieceService.markUnDone(userId, pieceId, isFinishedDto);
        return ResponseEntity.ok(ResponseDto.success(null));
    }

    @DeleteMapping("/studies/pieces")
    public ResponseEntity<ResponseDto<List<Object>>> deletePieces(
            @UserId final Long userId,
            @RequestBody @Valid final PieceDeleteRequestDto pieceDeleteRequestDto
    ) {
        pieceService.deletePieces(userId, pieceDeleteRequestDto);
        return ResponseEntity.ok(ResponseDto.success(null));
    }

    // 오늘 할 일 view + 정렬 API
    @GetMapping("/pieces/today/orders")
    public ResponseEntity<TodoPiecesResponse> getTodoPieces(
            @UserId final long userId,
            @RequestParam final String area, // todo 리스트인지, pending 리스트인지
            @RequestParam int year,
            @RequestParam final String semester,
            @RequestParam final String sortOption
    ) {
        return ResponseEntity.ok(pieceService.getPieces(userId, area, year, semester, sortOption));
    }

    // 오늘 할 공부 감추기 API
    @PostMapping("/pieces/hide")
    public ResponseEntity<ResponseDto<List<Object>>> hidePieces(
            @UserId final long userId,
            @RequestBody final PieceDeleteRequestDto pieceDeleteRequestDto
    ) {
        pieceService.updateStatusIsVisible(pieceDeleteRequestDto, userId);
        return ResponseEntity.ok(ResponseDto.success(null));
    }

    // 오늘 할 공부 추가 리스트 API
    @GetMapping("/pieces/todo")
    public ResponseEntity<AddTodoPiecesResponse> addTodayPiecesList(
            @UserId final long userId,
            @RequestParam final int year,
            @RequestParam final String semester,
            @RequestParam final String sortOption
    ){
        return ResponseEntity.ok(pieceService.getTodoList(userId, year, semester, sortOption));
    }

    // 오늘 할 공부로 추가하기 API
    @PostMapping("/pieces/assign-to-today")
    public ResponseEntity<ResponseDto<List<Object>>> addTodayPieces(
            @UserId final long userId,
            @RequestBody @Valid final PieceAddRequestDto pieceAddRequestDto
    ){
        pieceService.addTodoPieces(userId, pieceAddRequestDto);
        return ResponseEntity.ok(ResponseDto.success(null));
    }
}
