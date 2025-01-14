package com.sopt.bbangzip.fegin.kakao.client;

import com.sopt.bbangzip.common.constants.KakaoConstant;
import com.sopt.bbangzip.fegin.config.FeignConfig;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoUnlinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="KakaoUnlinkClient", url = KakaoConstant.KAKAO_USER_UNLINK_URL, configuration = FeignConfig.class)
public interface KakaoUnlinkClient {

    @PostMapping
    KakaoUnlinkResponse unlink(
            @RequestHeader(KakaoConstant.AUTHORIZATION) String accessToken
    );
}
