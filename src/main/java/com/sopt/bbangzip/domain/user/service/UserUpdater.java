package com.sopt.bbangzip.domain.user.service;

import com.sopt.bbangzip.domain.auth.OnboardingRequestDto;
import com.sopt.bbangzip.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdater {

    // 온보딩 완료 시점에 User 정보 수정하는 API
    public void registerUser(
            final User user,
            final OnboardingRequestDto onboardingRequestDto
    ){
        user.updateUser(onboardingRequestDto.nickname(), true);
    }
}