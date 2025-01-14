package com.sopt.bbangzip.domain.auth;

import com.sopt.bbangzip.domain.token.api.JwtTokensDto;
import com.sopt.bbangzip.domain.token.entity.Token;
import com.sopt.bbangzip.domain.token.service.TokenRemover;
import com.sopt.bbangzip.domain.token.service.TokenRetriever;
import com.sopt.bbangzip.domain.token.service.TokenSaver;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoUserInfoResponse;
import com.sopt.bbangzip.fegin.kakao.service.KakaoService;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoTokenResponse;
import com.sopt.bbangzip.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.core.env.Environment;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final Environment env;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRetriever userRetriever;

    private final TokenRetriever tokenRetriever;
    private final TokenSaver tokenSaver;
    private final TokenRemover tokenRemover;

    private final KakaoService kakaoService;

    @Transactional
    public JwtTokensDto kakaoLogin(final String code) {

        System.out.println("Kakao Client ID: " + kakaoClientId);
        System.out.println("Kakao Redirect URI: " + kakaoRedirectUri);

        // 1. kakao 토큰 요청
        KakaoTokenResponse kakaoTokenResponse = kakaoService.getToken(code, kakaoClientId, kakaoRedirectUri);
        // 2. kakao 사용자 정보 요청
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoService.getUserInfo(kakaoTokenResponse.accessToken());

        User user = userRetriever.findByPlatformUserId(
                kakaoUserInfoResponse.id()
        );

        JwtTokensDto jwtTokensDto = jwtTokenProvider.issueTokens(user.getId(), user.getIsOnboardingComplete());
        tokenSaver.save(Token.builder().id(user.getId()).refreshToken(jwtTokensDto.refreshToken()).build());
        return jwtTokensDto;
    }

    public JwtTokensDto reissueToken(final long userId) {
        return null;
    }

    public void logout(final long userId) {
        return;
    }

    public void unlink(final long userId) {
        return;
    }
}