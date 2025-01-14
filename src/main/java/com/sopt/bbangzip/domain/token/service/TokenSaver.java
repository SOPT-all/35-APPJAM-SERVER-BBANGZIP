package com.sopt.bbangzip.domain.token.service;

import com.sopt.bbangzip.domain.token.entity.Token;
import com.sopt.bbangzip.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenSaver {

    private final TokenRepository tokenRepository;

    public void save(final Token token) {
        tokenRepository.save(token);
    }
}