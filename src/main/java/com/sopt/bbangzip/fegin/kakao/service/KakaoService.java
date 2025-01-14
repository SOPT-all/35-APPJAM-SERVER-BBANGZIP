package com.sopt.bbangzip.fegin.kakao.service;

import com.sopt.bbangzip.common.constants.KakaoConstant;
import com.sopt.bbangzip.fegin.kakao.client.*;
import com.sopt.bbangzip.fegin.kakao.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoAuthClient kakaoAuthClient;
    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoReissueClient kakaoReissueClient;
    private final KakaoLogoutClient kakaoLogoutClient;
    private final KakaoUnlinkClient kakaoUnlinkClient;

    public KakaoTokenResponse getToken(
            final String code,
            final String clientId,
            final String redirectUri
    ) {

        if (clientId == null || redirectUri == null) {
            throw new IllegalArgumentException("clientId 또는 redirectUri가 null입니다.");
        }

        return kakaoAuthClient.kakaoAuth( // 카카오 인증 API 호출
                code, // 클라이언트에서 받은 인증 코드
                clientId, // 카카오 REST API Key
                redirectUri, // 카카오 Redirect URI
                KakaoConstant.AUTHORIZATION_CODE // 권한 부여 코드 타입 (authorization_code)
        );
    }

    public KakaoUserInfoResponse getUserInfo(
            final String accessToken
    ){
        return kakaoInfoClient.kakaoInfo(
                KakaoConstant.BEARER + accessToken
        );
    }

    public KakaoTokenResponse reissue(
            final KakaoReissueRequest request
    ) throws Exception {
        return kakaoReissueClient.kakaoReissue(request);
    }

    public KakaoLogoutResponse logout(
            final String accessToken
    ) {
        return kakaoLogoutClient.logout(
                KakaoConstant.BEARER + accessToken
        );
    }

    public KakaoUnlinkResponse unlink(
            final String accessToken
    ) {
        return kakaoUnlinkClient.unlink(
                KakaoConstant.BEARER + accessToken
        );
    }
}
