package com.sopt.bbangzip.domain.user.repository;

import com.sopt.bbangzip.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(final long userId);
    Optional<User> findByPlatformUserId(final Long platformUserId);
}
