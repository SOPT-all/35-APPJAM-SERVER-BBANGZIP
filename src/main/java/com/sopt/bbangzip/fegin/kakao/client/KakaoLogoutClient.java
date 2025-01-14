package com.sopt.bbangzip.fegin.kakao.client;

import com.sopt.bbangzip.common.constants.KakaoConstant;
import com.sopt.bbangzip.fegin.config.FeignConfig;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoLogoutResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="KakaoLogoutClient", url = KakaoConstant.KAKAO_USER_LOGOUT_URL, configuration = FeignConfig.class)
public interface KakaoLogoutClient {

    @GetMapping
    KakaoLogoutResponse logout(
            @RequestParam(KakaoConstant.AUTHORIZATION) final String accessToken
    );
}
