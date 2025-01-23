package com.sopt.bbangzip.domain.userSubject.service;

import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.repository.UserSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSubjectSaver {
    private final UserSubjectRepository userSubjectRepository;

    public UserSubject save(final UserSubject userSubject) {
        return userSubjectRepository.save(userSubject);
    }
}