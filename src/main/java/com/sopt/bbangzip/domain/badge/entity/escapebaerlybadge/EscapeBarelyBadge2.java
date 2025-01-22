package com.sopt.bbangzip.domain.badge.entity.escapebaerlybadge;

import com.sopt.bbangzip.domain.badge.entity.Badge;
import com.sopt.bbangzip.domain.badge.entity.BadgeCondition;
import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EscapeBarelyBadge2 implements Badge {
    @Override
    public BadgeCondition getCondition() {
        return user -> user.getFirstCreateStudyCount() == -1; //걍 말도 안되는 조
    }

    @Override
    public String getName() {
        return "미룬이겨우탈출2";
    }

    @Override
    public int getReward() {
        return 100;
    }

    public List<String> getHashTags() {
        return List.of("#앱잼내", "#그냥 보여주는", "#유령뱃지");
    }

    @Override
    public String getImage() {
        return "https://github.com/user-attachments/assets/0bf40464-e538-49fe-96d0-d2f3181b55e0";
    }

    @Override
    public String getCategory() {
        return "미룬이 겨우 탈출";
    }

    @Override
    public Boolean isLocked(User user) {
        return true;
    }

    @Override
    public String getAchievementCondition() {
        return "몰라조건몰라";
    }
}
