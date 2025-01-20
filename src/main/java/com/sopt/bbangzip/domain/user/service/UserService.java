package com.sopt.bbangzip.domain.user.service;

import com.sopt.bbangzip.domain.user.api.dto.UserLevelResponseDto;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRetriever userRetriever;
    private final UserLevelCalculator userLevelCalculator;
    private final UserRepository userRepository;

    @Transactional
    public UserLevelResponseDto updateAndGetUserLevelStatus(Long userId) {
        User user = userRetriever.findByUserId(userId);

        // 새로운 레벨 및 최대 포인트 계산
        UserLevelCalculator.LevelInfo levelInfo = userLevelCalculator.calculateLevelInfo(user.getPoint());

        // 유저의 레벨 업데이트
        user.updateUserLevel(levelInfo.getLevel());
        userRepository.save(user);

        // DTO 반환
        return new UserLevelResponseDto(
                user.getUserLevel(),
                user.getBadgeCount(),
                user.getPoint(),
                levelInfo.getMaxReward()
        );
    }
}
