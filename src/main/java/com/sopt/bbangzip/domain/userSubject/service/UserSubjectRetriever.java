package com.sopt.bbangzip.domain.userSubject.service;

import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import com.sopt.bbangzip.domain.userSubject.repository.UserSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSubjectRetriever {

    private final UserSubjectRepository userSubjectRepository;
    private final UserRetriever userRetriever;

    // 해당 userId 의 Year 와 Semester 에 해당하는 과목이 있는지 확인하는 애자나
    public UserSubject findByUserIdAndYearAndSemester(Long userId, int year, String semester){
        User user = userRetriever.findByUserId(userId);
        return userSubjectRepository.findByUserIdAndYearAndSemester(userId, year, semester)
                .orElseGet(() -> UserSubject.builder().user(user).year(year).semester(semester).build());
    }
}