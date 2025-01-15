package com.sopt.bbangzip.domain.token.service;

import com.sopt.bbangzip.domain.token.entity.Token;
import com.sopt.bbangzip.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenRetriever {

    private final TokenRepository tokenRepository;

    public Optional<Token> findByRefreshToken(final String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }
}