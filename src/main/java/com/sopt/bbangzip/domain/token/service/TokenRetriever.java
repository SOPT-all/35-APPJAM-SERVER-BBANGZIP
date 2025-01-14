package com.sopt.bbangzip.domain.token.service;

import com.sopt.bbangzip.domain.token.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRetriever {

    private final TokenRepository tokenRepository;
}