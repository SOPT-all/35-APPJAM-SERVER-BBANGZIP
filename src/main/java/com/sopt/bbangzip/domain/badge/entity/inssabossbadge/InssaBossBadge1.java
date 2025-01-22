package com.sopt.bbangzip.domain.badge.entity.inssabossbadge;

import com.sopt.bbangzip.domain.badge.entity.Badge;
import com.sopt.bbangzip.domain.badge.entity.BadgeCondition;
import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InssaBossBadge1 implements Badge {
    @Override
    public BadgeCondition getCondition() {
        return user -> user.getFirstCreateStudyCount() == -1; //걍 말도 안되는 조
    }

    @Override
    public String getName() {
        return "인싸보스1";
    }

    @Override
    public int getReward() {
        return 100;
    }

    @Override
    public List<String> getHashTags() {
        return null;
    }

    @Override
    public String getImage() {
        return "https://github.com/user-attachments/assets/76951d0e-808d-4d4a-83d0-294102940eb3";
    }

    @Override
    public String getCategory() {
        return "인싸 사장님";
    }

    @Override
    public Boolean isLocked(User user) {
        return true;
    }

    @Override
    public String getAchievementCondition() {
        return null;
    }
}
