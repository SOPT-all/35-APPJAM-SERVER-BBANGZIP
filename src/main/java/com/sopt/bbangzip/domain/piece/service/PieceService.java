package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.badge.BadgeResponse;
import com.sopt.bbangzip.domain.badge.BadgeService;
import com.sopt.bbangzip.domain.piece.api.dto.request.IsFinishedDto;
import com.sopt.bbangzip.domain.piece.api.dto.response.MarkDoneResponse;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PieceService {

    private final UserRetriever userRetriever;
    private final BadgeService badgeService;
    private final PieceRetriever pieceRetriever;
    private final PieceUpdater pieceUpdater;

    @Transactional
    public MarkDoneResponse updateStatus(
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
        List<BadgeResponse> newlyAwardedBadges = pieceUpdater.updateStatus(piece, isFinishedDto, user);


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
}