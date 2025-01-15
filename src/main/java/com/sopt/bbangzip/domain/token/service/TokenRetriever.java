package com.sopt.bbangzip.domain.token.service;

import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.token.entity.Token;
import com.sopt.bbangzip.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRetriever {

    private final TokenRepository tokenRepository;

    public Token findById(final Long userId) {
        return tokenRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_TOKEN)
        );
    }

    public Optional<Token> findByRefreshToken(final String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }
}