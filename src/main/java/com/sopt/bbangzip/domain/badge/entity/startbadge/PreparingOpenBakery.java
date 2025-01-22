package com.sopt.bbangzip.domain.badge.entity.startbadge;

import com.sopt.bbangzip.domain.badge.entity.Badge;
import com.sopt.bbangzip.domain.badge.entity.BadgeCondition;
import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PreparingOpenBakery implements Badge {
    @Override
    public BadgeCondition getCondition() {
        return user -> user.getFirstCreateStudyCount() == 0 && user.getHasPreparingOpeningBakery() == null;
    }

    @Override
    public String getName() {
        return "빵집 오픈 준비 중";
    }

    @Override
    public int getReward() {
        return 50;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#오늘은 무슨 빵을 구울까", "#빵 냄새 솔솔", "#노릇노릇");
    }

    @Override
    public String getImage() { return "https://github.com/user-attachments/assets/3904cdbf-6329-41fc-9059-c3d785d84eb1";}

    @Override
    public String getCategory() {
        return "시작이 빵이다";
    }

    @Override
    public Boolean isLocked(User user) {return user.getHasPreparingOpeningBakery() == null;}

    @Override
    public String getAchievementCondition() {
        return "최초로 '학습 완료'를 수행한 경우";
    }
}
