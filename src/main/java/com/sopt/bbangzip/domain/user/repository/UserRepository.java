package com.sopt.bbangzip.domain.user.repository;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
