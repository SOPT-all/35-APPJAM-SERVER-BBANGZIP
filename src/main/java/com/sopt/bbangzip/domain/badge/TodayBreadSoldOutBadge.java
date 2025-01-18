package com.sopt.bbangzip.domain.badge;

import com.sopt.bbangzip.domain.piece.service.PieceRetriever;
import com.sopt.bbangzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TodayBreadSoldOutBadge implements Badge{

    private final PieceRetriever pieceRetriever;

    public BadgeCondition getCondition() {
        return user -> {
            // 아직 뱃지를 획득하지 않았고, 오늘 할 일이 0개일 때만 조건을 만족
            int todayCounts = pieceRetriever.countUnfinishedTodayPieces(user.getId());
            int completeCounts = pieceRetriever.countFinishedTodayPieces(user.getId());

            // 오늘 할 일이 할당되지 않았을 경우에는 뱃지 획득 못함
            if (todayCounts + completeCounts == 0) {
                return false;
            }
            return user.getAllTasksCompletedAt() == null && todayCounts == 0;
        };
    }

    @Override
    public String getName() {
        return "오늘의 빵 완판";
    }

    @Override
    public int getReward() {
        return 200;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#준비한 수량이 모두 소진되었습니다", "#내일 다시 방문해 주세요");
    }

    @Override
    public String getImage() {
        return "https://example.com/images/2";
    }

    @Override
    public String getCategory() {
        return "시작이 빵이다";
    }

    @Override
    public Boolean isLocked(User user) {
        // 유저가 조건을 만족하면 잠금 해제
        return !getCondition().isEligible(user);
    }
}