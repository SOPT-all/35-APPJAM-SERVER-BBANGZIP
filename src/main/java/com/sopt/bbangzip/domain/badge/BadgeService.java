package com.sopt.bbangzip.domain.badge;

import com.sopt.bbangzip.domain.piece.service.PieceRetriever;
import com.sopt.bbangzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {

    /**
     * - 모든 뱃지의 목록 관리
     * - 사용자에게 조건에 맞는 뱃지 지급
     *
     * - 모든 뱃지는 한 번씩만 획득할 수 있다!!
     */

    private final List<Badge> badges;
    private final PieceRetriever pieceRetriever;

    /**
     * 조건에 맞는 모든 뱃지를 반환
     */
    public List<BadgeResponse> getAllEligibleBadges(User user) {
        List<BadgeResponse> awardedBadges = new ArrayList<>();

        for (Badge badge : badges) {
            BadgeCondition condition = badge.getCondition();
            if (condition.isEligible(user) && !isBadgeAlreadyAwarded(user, badge)) {
                awardBadge(user, badge); // 뱃지를 부여
                awardedBadges.add(new BadgeResponse(
                        badge.getName(),
                        badge.getImage(),
                        badge.getHashTags()
                ));
            }
        }
        return awardedBadges; // 조건에 맞는 모든 뱃지를 반환
    }

    private boolean isBadgeAlreadyAwarded(User user, Badge badge) {
        int todayCounts = pieceRetriever.countUnfinishedTodayPieces(user.getId());
        return switch (badge.getName()) {
            case "빵 굽기 시작" -> user.getFirstStudyCompletedAt() != null;
            case "오늘의 빵 완판" -> user.getAllTasksCompletedAt() != null;
            case "빵 대량 생산" -> user.getHasMassBakingBreadBadge() != null;
            default -> false;
        };
    }

    // 뱃지 부여
    private void awardBadge(User user, Badge badge) {
        // 유저가 어떤 뱃지를 얻었는지와 포인트에 대한 특정 필드를 업데이트 시키자
        switch (badge.getName()) {
            case "빵 굽기 시작" -> user.markFirstStudyComplete();
            case "오늘의 빵 완판" -> user.markFirstTodayTasksCompletedAt();
            case "빵 대량 생산" -> user.markHasMassBakingBreadBadge();
            default -> throw new IllegalArgumentException();
        }
        log.info(badge.getName() + "뱃지를 획득하였습니다!");
    }
}