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
        // 새로운 레벨 계산 및 업데이트
        int newLevel = userLevelCalculator.calculateLevel(user.getPoint());
        user.updateUserLevel(newLevel);
        userRepository.save(user);
        return new UserLevelResponseDto(user.getUserLevel(), user.getBadgeCount(), user.getPoint());
    }

}
