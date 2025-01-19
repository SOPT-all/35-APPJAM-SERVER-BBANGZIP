package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeResponse;
import com.sopt.bbangzip.domain.piece.api.dto.request.IsFinishedDto;
import com.sopt.bbangzip.domain.piece.api.dto.request.PieceDeleteRequestDto;
import com.sopt.bbangzip.domain.piece.api.dto.response.AddTodoPiecesResponse;
import com.sopt.bbangzip.domain.piece.api.dto.response.MarkDoneResponse;
import com.sopt.bbangzip.domain.piece.api.dto.response.TodoPiecesResponse;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.piece.repository.PieceRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PieceService {

    private final UserRetriever userRetriever;
  
    private final PieceRetriever pieceRetriever;
    private final PieceUpdater pieceUpdater;
    private final PieceSaver pieceSaver;
  
    private final PieceRemover pieceRemover;

    @Transactional
    public void deletePieces(PieceDeleteRequestDto pieceDeleteRequestDto) {
        List<Long> pieceIds = pieceDeleteRequestDto.pieceIds();
        List<Piece> pieces = pieceRetriever.findAllByIds(pieceIds);

        // 유효한 조각인지 검증
        if (pieces.isEmpty() || pieces.size() != pieceIds.size()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_PIECE);
        }

        pieceRemover.removePieces(pieces);
    }

    @Transactional
    public MarkDoneResponse markDone(
            final Long userId,
            final Long pieceId,
            IsFinishedDto isFinishedDto
    ) {
        // 1. 유저가 선택한 Piece 와 User 를 조회하자
        Piece piece = pieceRetriever.findByPieceIdAndUserId(pieceId, userId);
        User user = userRetriever.findByUserId(userId);

        // 2. 공부 조각을 상태를 완료로 바꾸면서,
        // 뱃지를 얻어야하는 상황인지 검증하고, 부여 까지 여기서 다 한다.
        // 얻은 뱃지가 있다면 뱃지 반환하고, 없다면 null 이 반환됨
        List<BadgeResponse> newlyAwardedBadges = pieceUpdater.updateStatusDone(piece, isFinishedDto, user);

        // 3. 오늘 남은 총 공부 개수 확인
        int todayCounts = pieceRetriever.countUnfinishedTodayPieces(userId);
        // 3. 오늘 완료한 총 공부 개수 확인
        int completeCounts = pieceRetriever.countFinishedTodayPieces(userId);

        if (newlyAwardedBadges.isEmpty()) {
            // 뱃지 획득하지 않은 경우
            return MarkDoneResponse.builder()
                    .todayCounts(todayCounts)
                    .completeCounts(completeCounts)
                    .badges(null) // 뱃지 없음
                    .build();
        } else {
            // 뱃지 획득한 경우
            return MarkDoneResponse.builder()
                    .todayCounts(todayCounts)
                    .completeCounts(completeCounts)
                    .badges(newlyAwardedBadges) // 획득한 뱃지 목록 반환
                    .build();
        }
    }

    @Transactional
    public void markUnDone(
            final Long userId,
            final Long pieceId,
            IsFinishedDto isFinishedDto
    ) {
        Piece piece = pieceRetriever.findByPieceIdAndUserId(pieceId, userId);
        User user = userRetriever.findByUserId(userId);
        pieceUpdater.updateStatusUnDone(piece, isFinishedDto, user);
    }

    public TodoPiecesResponse getPieces(
            final Long userId,
            final String area, // "todo" 또는 "pending"
            final int year,
            final String semester,
            final String sortOption
    ) {
        User user = userRetriever.findByUserId(userId);

            /**
             *  area 가 todo 라면 '오늘 할 일' 뷰
             *  오늘 할 일 : is_visible 이 true 여야 됨
             */
        List<Piece> pieces;
        if (area.equals("todo")) {
            pieces = switch (sortOption) {
                case "recent" -> pieceRetriever.findTodoPiecesByRecentOrder(userId, year, semester);
                case "leastVolume" -> pieceRetriever.findTodoPiecesByLeastVolumeOrder(userId, year, semester);
                case "nearestDeadline" -> pieceRetriever.findTodoPiecesByNearestDeadlineOrder(userId, year, semester);
                default -> throw new IllegalArgumentException("Invalid sort option: " + sortOption);
            };
        } else {
            /**
             * area 가 pending 이라면 '밀린 일' 뷰
             * 밀린 일 : is_visible 이 false 면서, is_finished 가 false 면서, 오늘 날짜가 piece 의 deadline 보다 이후인 piece 들
             */
            pieces = switch (sortOption) {
                case "recent" -> pieceRetriever.findPendingPiecesByRecentOrder(userId, year, semester);
                case "leastVolume" -> pieceRetriever.findPendingPiecesByLeastVolumeOrder(userId, year, semester);
                case "nearestDeadline" -> pieceRetriever.findPendingPiecesByNearestDeadlineOrder(userId, year, semester);
                default -> throw new IllegalArgumentException("Invalid sort option: " + sortOption);
            };
        }

        int todayCount = pieceRetriever.countUnfinishedTodayPieces(userId);
        int completedCount = pieceRetriever.countFinishedTodayPieces(userId);
        int pendingCount = pieceRetriever.countPendingTodayPieces(userId);

        // 5. Piece 데이터를 TodoPieceDto로 변환
        List<TodoPiecesResponse.TodoPieceDto> todoPieceDtos = pieces.stream()
                .map(piece -> TodoPiecesResponse.TodoPieceDto.builder()
                        .pieceId(piece.getId())
                        .subjectName(piece.getStudy().getExam().getSubject().getSubjectName())
                        .examName(piece.getStudy().getExam().getExamName())
                        .studyContents(piece.getStudy().getStudyContents())
                        .startPage(piece.getStartPage())
                        .finishPage(piece.getFinishPage())
                        .deadline(piece.getDeadline().toString())
                        .remainingDays(calculateRemainingDays(piece.getDeadline()))
                        .isFinished(piece.getIsFinished())
                        .build())
                .toList();

        return TodoPiecesResponse.builder()
                .todayCount(area.equals("todo") ? todayCount : null) // "pending"일 경우 null
                .completeCount(area.equals("todo") ? completedCount : null) // "pending"일 경우 null
                .pendingCount(pendingCount)
                .todoPiecesList(todoPieceDtos)
                .build();
    }

    private int calculateRemainingDays(LocalDate deadline) {
        int remainingDays = (int) ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        return remainingDays >= 0 ? -remainingDays : Math.abs(remainingDays); // 밀린 일 양수, 남은 일 음수
    }

    @Transactional
    public void updateStatusIsVisible(PieceDeleteRequestDto pieceDeleteRequestDto) {
        List<Long> pieceIds = pieceDeleteRequestDto.pieceIds();
        List<Piece> pieces = pieceRetriever.findAllByIds(pieceIds);

        if (pieces.isEmpty() || pieces.size() != pieceIds.size()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_PIECE);
        }
        pieces.forEach(piece -> piece.updateIsVisible(false));
        pieceSaver.saveAll(pieces);
    }

    public AddTodoPiecesResponse getTodoList(
            final Long userId,
            final int year,
            final String semester,
            final String sortOption
    ) {
        User user = userRetriever.findByUserId(userId);
        int todoCount = pieceRetriever.findAddTodoPieceCount(userId, year, semester);

        List<Piece> pieces;
        pieces = switch (sortOption) {
            case "recent" -> pieceRetriever.findAddTodoPieceListByRecentOrder(userId, year, semester);
            case "leastVolume" -> pieceRetriever.findAddTodoPieceListByLeastVolumeOrder(userId, year, semester);
            case "nearestDeadline" -> pieceRetriever.findAddTodoPieceListByNearestDeadlineOrder(userId, year, semester);
            default -> throw new IllegalArgumentException("Invalid sort option: " + sortOption);
        };

        List<AddTodoPiecesResponse.TodoList> todoLists = pieces.stream()
                .map(piece -> AddTodoPiecesResponse.TodoList.builder()
                        .pieceId(piece.getId())
                        .subjectName(piece.getStudy().getExam().getSubject().getSubjectName())
                        .examName(piece.getStudy().getExam().getExamName())
                        .studyContents(piece.getStudy().getStudyContents())
                        .startPage(piece.getStartPage())
                        .finishPage(piece.getFinishPage())
                        .deadline(piece.getDeadline().toString())
                        .remainingDays(calculateRemainingDays(piece.getDeadline()))
                        .build())
                .toList();

        return AddTodoPiecesResponse.builder()
                .todoCount(todoCount)
                .todoLists(todoLists)
                .build();
    }
}
