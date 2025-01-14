package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.user.repository.UserRepository;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.repository.UserSubectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectRetriever {

    private final UserRepository userRepository;
    private final UserSubectRepository userSubectRepository;

    // 유저 존재 여부 확인
    public void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(ErrorCode.UNAUTHORIZED);
        }
    }

    // UserSubject 조회 및 생성
    public UserSubject findOrCreateUserSubject(Long userId, int year, String semester) {
        return userSubectRepository.findByUser_IdAndYearAndSemester(userId, year, semester)
                .orElseGet(() -> userSubectRepository.save(
                        new UserSubject(
                                userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.UNAUTHORIZED)),
                                year,
                                semester
                        )
                ));
    }
}
