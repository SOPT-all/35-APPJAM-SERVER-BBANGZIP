package com.sopt.bbangzip.domain.token.repository;

import com.sopt.bbangzip.domain.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByRefreshToken(final String refreshToken);

    Optional<Token> findById(final Long id);
}
