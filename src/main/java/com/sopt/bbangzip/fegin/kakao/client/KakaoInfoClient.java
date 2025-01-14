package com.sopt.bbangzip.fegin.kakao.client;

import com.sopt.bbangzip.common.constants.KakaoConstant;
import com.sopt.bbangzip.fegin.config.FeignConfig;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoUserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoInfoClient", url = KakaoConstant.KAKAO_USER_INFO_URL, configuration = FeignConfig.class)
public interface KakaoInfoClient {
    @GetMapping
    KakaoUserInfoResponse kakaoInfo(
            @RequestHeader(KakaoConstant.AUTHORIZATION) final String accessToken
    );
}
