package com.sopt.bbangzip.domain.subject.repository;

import com.sopt.bbangzip.domain.subject.entity.Subject;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    //  UserSubject와 SubjectName으로 Subject 존재 여부 확인
    boolean existsByUserSubjectAndSubjectName(UserSubject userSubject, String subjectName);
    // 특정 UserSubject ID와 과목 ID로 과목 조회
    List<Subject> findByIdInAndUserSubjectId(List<Long> subjectIds, Long userSubjectId);

    Optional<Subject> findById(Long subjectId);

    // UserSubject와 SubjectName으로 Subject 조회
    Optional<Subject> findByUserSubjectAndSubjectName(UserSubject userSubject, String subjectName);

    Optional<Subject> findByUserSubject_UserIdAndIdAndSubjectName(Long userId, Long subjectId, String subjectName);
}
