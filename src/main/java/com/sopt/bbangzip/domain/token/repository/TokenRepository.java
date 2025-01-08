package com.sopt.bbangzip.domain.token.repository;

import com.sopt.bbangzip.domain.token.entity.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
