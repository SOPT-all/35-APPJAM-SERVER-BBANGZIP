package com.sopt.bbangzip.domain.userSubject.repository;

import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {

    // userId, year, semester를 통해 UserSubject 조회
    Optional<UserSubject> findByUserIdAndYearAndSemester(final Long userId,  final int year, final String semester);

    // userId와 userSubjectId로 UserSubject 조회
    Optional<UserSubject> findByUserIdAndId(final Long userId, final Long userSubjectId);

}

