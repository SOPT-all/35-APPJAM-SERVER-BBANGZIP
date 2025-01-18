package com.sopt.bbangzip.domain.user.api.controller;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.user.api.dto.UserLevelResponseDto;
import com.sopt.bbangzip.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/mypage")
    public ResponseEntity<UserLevelResponseDto> getMyPage(
            @UserId final Long userId
    ) {
        // 마이페이지 조회 시 유저 레벨 업데이트
        UserLevelResponseDto responseDto = userService.updateAndGetUserLevelStatus(userId);
        return ResponseEntity.ok(responseDto);
    }

}
