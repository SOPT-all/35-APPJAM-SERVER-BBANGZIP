package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public int countPendingTodayPieces(final Long userId){
        return pieceRepository.countPendingTodayPieces(userId);
    }

    /*
    Todo
     */
    public List<Piece> findTodoPiecesByRecentOrder(Long userId, int year, String semester){
        return  pieceRepository.findTodoPiecesByRecentOrder(userId, year, semester);
    }

    public List<Piece> findTodoPiecesByLeastVolumeOrder(Long userId, int year, String semester){
        return pieceRepository.findTodoPiecesByLeastVolumeOrder(userId, year, semester);
    }

    public List<Piece> findTodoPiecesByNearestDeadlineOrder(Long userId, int year, String semester){
        return pieceRepository.findTodoPiecesByNearestDeadlineOrder(userId, year, semester);
    }

    /*
    Pending
     */
    public List<Piece> findPendingPiecesByRecentOrder(Long userId, int year, String semester){
        return pieceRepository.findPendingPiecesByRecentOrder(userId, year, semester);
    }

    public List<Piece> findPendingPiecesByLeastVolumeOrder(Long userId, int year, String semester){
        return pieceRepository.findPendingPiecesByLeastVolumeOrder(userId, year, semester);
    }

    public List<Piece> findPendingPiecesByNearestDeadlineOrder(Long userId, int year, String semester){
        return pieceRepository.findPendingPiecesByNearestDeadlineOrder(userId, year, semester);
    }

    /*
    Add Todo
     */

    public int findAddTodoPieceCount(Long userId, int year, String semester){
        return pieceRepository.findAddTodoPieceCount(userId, year, semester);
    }

    public List<Piece> findAddTodoPieceListByRecentOrder(Long userId, int year,  String semester){
        return pieceRepository.findAddTodoPieceListByRecentOrder(userId, year, semester);
    }

    public List<Piece> findAddTodoPieceListByLeastVolumeOrder(Long userId, int year,  String semester){
        return pieceRepository.findAddTodoPieceListByLeastVolumeOrder(userId, year, semester);
    }

    public List<Piece> findAddTodoPieceListByNearestDeadlineOrder(Long userId, int year,  String semester){
        return pieceRepository.findAddTodoPieceListByNearestDeadlineOrder(userId, year, semester);
    }

    /*
    Add Pending
     */

    public List<Piece> findByStudyExamIdWithUser(Long examId) {
        return pieceRepository.findByStudyExamIdWithUser(examId);
    }

      // 특정 ID 리스트에 해당하는 조각(Piece)들을 조회하는 메서드
    public List<Piece> findAllByIds(List<Long> pieceIds) {
        return pieceRepository.findAllById(pieceIds);
    }
}