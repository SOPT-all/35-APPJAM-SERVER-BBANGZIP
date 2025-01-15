package com.sopt.bbangzip.domain.user.service;

import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRemover {

    private final UserRepository userRepository;

    public void removeUser(final User user) {
        userRepository.delete(user);
    }
}