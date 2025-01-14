package com.sopt.bbangzip.domain.userSubject.repository;

import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.userSubject.entity.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSubectRepository extends JpaRepository<UserSubject, Long> {
    Optional<UserSubject> findByUser_IdAndYearAndSemester(
            Long userId,  int year, String semester
    );
}

