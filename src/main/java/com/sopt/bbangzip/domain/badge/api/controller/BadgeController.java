package com.sopt.bbangzip.domain.badge.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.common.dto.ResponseDto;
import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeDetailResponse;
import com.sopt.bbangzip.domain.badge.api.dto.response.BadgeListResponse;
import com.sopt.bbangzip.domain.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("/mypage/badges")
    public ResponseEntity<BadgeListResponse> getBadgeList(
            @UserId final Long userId
    ) {
        return ResponseEntity.ok(badgeService.getBadgeList(userId));
    }

    @GetMapping("/mypage/badges/{badgeName}")
    public ResponseEntity<BadgeDetailResponse> getBadgeDetails(
            @PathVariable String badgeName,
            @UserId final Long userId
    ) {
        return ResponseEntity.ok(badgeService.getBadgeDetail(userId, badgeName));
    }
}