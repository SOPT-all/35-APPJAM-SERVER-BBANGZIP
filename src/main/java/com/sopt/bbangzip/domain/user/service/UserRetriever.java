package com.sopt.bbangzip.domain.user.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRetriever {

    private final UserRepository userRepository;

    public User findByUserId(final long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER));
    }

    public User findByPlatformUserId(final Long platformUserId) {
        return userRepository.findByPlatformUserId(platformUserId).orElseGet(
                () -> userRepository.save(User.builder().platformUserId(platformUserId).platform("KAKAO").isOnboardingComplete(false).userLevel(0L).build())
        );
    }
}