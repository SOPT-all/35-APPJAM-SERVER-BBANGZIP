package com.sopt.bbangzip.security.jwt;

import com.sopt.bbangzip.common.constants.AuthConstant;
import com.sopt.bbangzip.common.exception.base.NotFoundException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import com.sopt.bbangzip.domain.token.api.JwtTokensDto;
import com.sopt.bbangzip.domain.token.api.ReissueJwtTokensDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

/*
   사용자 ID 사용해서 JWT 토큰 생성하는 클래스
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh-token-expire-time}")
    @Getter
    private long REFRESH_TOKEN_EXPIRE_TIME;

    // JWT 서명을 위한 HMAC 키 객체
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // accessToken, RefreshToken 만들어서 반환
    public JwtTokensDto issueTokens(final long userId, final boolean isOnboardingComplete) {
        return JwtTokensDto.builder()
                .accessToken(generateToken(userId, ACCESS_TOKEN_EXPIRE_TIME, "ACCESS_TOKEN"))
                .refreshToken(generateToken(userId, REFRESH_TOKEN_EXPIRE_TIME, "REFRESH_TOKEN"))
                .isOnboardingComplete(isOnboardingComplete)
                .build();
    }

    // 재발급
    public ReissueJwtTokensDto reissueTokens(final long userId) {
        return ReissueJwtTokensDto.builder()
                .accessToken(generateToken(userId, ACCESS_TOKEN_EXPIRE_TIME, "ACCESS_TOKEN"))
                .refreshToken(generateToken(userId, REFRESH_TOKEN_EXPIRE_TIME, "REFRESH_TOKEN"))
                .build();
    }

    public void validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 토큰 타입이 REFRESH_TOKEN인지 확인
            String tokenType = claims.get("type", String.class);
            if (!"REFRESH_TOKEN".equals(tokenType)) {
                throw new NotFoundException(ErrorCode.TYPE_ERROR_JWT_TOKEN);
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new NotFoundException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    // JWT 토큰 생성
    public String generateToken(final long userId, final long tokenExpirationTime, final String tokenType) {
        final Date now = new Date(System.currentTimeMillis());
        final Date expirationTime = new Date(System.currentTimeMillis() + tokenExpirationTime);

        final Claims claims = Jwts.claims()
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(expirationTime) // 토큰 만료 시간
                .setSubject(tokenType); // 토큰 타입 설정

        claims.put(AuthConstant.USER_ID_CLAIM_NAME, userId); // 사용자 ID 추가
        claims.put("type", tokenType); // "type" 클레임 추가

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰을 파싱하고 JWS 검증, JWT의 클레임 반환
    public Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 요청의 헤더에서 토큰을 찾아주는 함수
    public String getJwtFromRequest(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthConstant.BEARER_PREFIX)) {
            return bearerToken.substring(AuthConstant.BEARER_PREFIX.length());
        }
        return null;
    }
}