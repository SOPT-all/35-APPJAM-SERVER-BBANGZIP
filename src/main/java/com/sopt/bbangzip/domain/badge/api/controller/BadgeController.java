package com.sopt.bbangzip.domain.badge.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeListResponse;
import com.sopt.bbangzip.domain.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/mypage/badges")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<BadgeListResponse> getBadgeList(@UserId final Long userId) {
        BadgeListResponse badgeList = badgeService.getBadgeList(userId);
        return ResponseEntity.ok(badgeList);
    }

}