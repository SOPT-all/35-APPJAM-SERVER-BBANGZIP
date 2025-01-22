package com.sopt.bbangzip.domain.badge.entity.escapebadge;

import com.sopt.bbangzip.domain.badge.entity.Badge;
import com.sopt.bbangzip.domain.badge.entity.BadgeCondition;
import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EscapeBadgeAapjam implements Badge {
    @Override
    public BadgeCondition getCondition() {
        return user -> user.getFirstCreateStudyCount() == -1; //걍 말도 안되는 조건
    }

    @Override
    public String getName() {
        return "미룬이탈출앱잼";
    }

    @Override
    public int getReward() {
        return 100;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#앱잼내", "#그냥 보여주는", "#유령뱃지");
    }

    @Override
    public String getImage() {
        return "https://github.com/user-attachments/assets/6255389d-5392-4150-bd30-7e77d5c296c3";
    }

    @Override
    public String getCategory() {
        return "미룬이 탈출";
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
