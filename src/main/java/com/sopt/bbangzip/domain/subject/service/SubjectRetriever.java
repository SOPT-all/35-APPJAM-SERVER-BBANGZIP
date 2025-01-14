package com.sopt.bbangzip.domain.subject.service;

import com.sopt.bbangzip.domain.subject.repository.SubjectRepository;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectRetriever {

    private final SubjectRepository subjectRepository;

    // userSubject subjectName 에 해당하는 과목(Subject) 가 존재하는지 확인하는 메서드
    public boolean existsByUserSubjectAndSubjectName(UserSubject userSubject, String subjectName){
        return subjectRepository.existsByUserSubjectAndSubjectName(userSubject, subjectName);
    }
}
