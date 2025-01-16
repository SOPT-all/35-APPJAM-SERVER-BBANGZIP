package com.sopt.bbangzip.domain.badge;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MassBakingBreadBadge implements Badge{
    @Override
    public BadgeCondition getCondition(){
        return user -> user.getTodayStudyCompleteCount() >= 3;
    }

    @Override
    public String getName() {
        return "빵 대량 생산";
    }

    @Override
    public int getReward() {
        return 50;
    }

    @Override
    public List<String> getHashTags() {
        return List.of("#사장님은 열일 중", "#오늘 빵 몇 개 구울 거야", "#백만 개");
    }

    @Override
    public String getImage() {
        return "https://example.com/images/3";
    }
}