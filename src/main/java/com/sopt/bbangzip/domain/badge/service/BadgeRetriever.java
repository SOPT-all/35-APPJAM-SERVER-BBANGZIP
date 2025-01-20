package com.sopt.bbangzip.domain.badge.service;

import com.sopt.bbangzip.domain.badge.entity.Badge;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 대환핑이 짜줌
 * 나중에 이거 참고해서 시간 남으면 리팩 할 것.
 */
@Component
public class BadgeRetriever {
    private final List <Badge> badges;

    public BadgeRetriever(List<Badge> badges) {
        this.badges = badges;
    }

    public List<Badge> getBadges() {
        return badges;
    }
}