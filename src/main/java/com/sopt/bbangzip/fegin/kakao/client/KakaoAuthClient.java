package com.sopt.bbangzip.fegin.kakao.client;

import com.sopt.bbangzip.common.constants.KakaoConstant;
import com.sopt.bbangzip.fegin.config.FeignConfig;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "KakaoAuthClient", url = KakaoConstant.KAKAO_TOKEN_URL, configuration = FeignConfig.class)
public interface KakaoAuthClient {

    @PostMapping
    KakaoTokenResponse kakaoAuth(
            @RequestParam(KakaoConstant.CODE) final String code,
            @RequestParam(KakaoConstant.CLIENT_ID) final String clientId,
            @RequestParam(KakaoConstant.REDIRECT_URI) final String redirectUri,
            @RequestParam(KakaoConstant.GRANT_TYPE) final String grantType);
}
