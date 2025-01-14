package com.sopt.bbangzip.domain.userSubject.repository;

import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {
    Optional<UserSubject> findByUserIdAndYearAndSemester(Long userId,  int year, String semester);
}

