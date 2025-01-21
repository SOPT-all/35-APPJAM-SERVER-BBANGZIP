package com.sopt.bbangzip.domain.piece.service;

import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeResponse;
import com.sopt.bbangzip.domain.badge.service.BadgeService;
import com.sopt.bbangzip.domain.piece.api.dto.request.IsFinishedDto;
import com.sopt.bbangzip.domain.piece.entity.Piece;
import com.sopt.bbangzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PieceUpdater {

    private final BadgeService badgeService;

    public List<BadgeResponse> updateStatusDone(
            Piece piece,
            IsFinishedDto isFinished,
            User user
    ) {
        // 1. 공부 조각 상태를 업데이트
        piece.updateStatus(isFinished.isFinished());

        // 2. 유저의 '오늘 완료 학습 횟수' 증가 및 '마지막 학습 완료 날짜' 업데이트
        if (isFinished.isFinished()) {
            user.incrementTodayStudyCompleteCount();

            // 3. '빵 굽기 시작' 뱃지 평가 (첫 번째 학습 완료 관련)
            badgeService.checkForStartBakingBreadBadge(user);

            // 3. 뱃지 조건 체크 후, 새로 획득한 뱃지 리스트 반환
            return badgeService.getAllEligibleBadges(user);
        }
        // 상태가 완료로 변경되지 않았다면, 뱃지 반환하지 않음
        return null;
    }

    public void updateStatusUnDone(
            Piece piece,
            IsFinishedDto isFinished,
            User user
    ){
        piece.updateStatus(isFinished.isFinished());
    }

    public void updateStatusIsVisible(
            List<Piece> pieces,
            User user
    ){
        // 공부 조각들의 상태 is_visible 을 true 로 업데이트
        pieces.forEach(piece -> piece.updateIsVisible(true));
    }
}