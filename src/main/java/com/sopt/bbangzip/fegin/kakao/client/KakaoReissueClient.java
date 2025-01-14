package com.sopt.bbangzip.fegin.kakao.client;

import com.sopt.bbangzip.common.constants.KakaoConstant;
import com.sopt.bbangzip.fegin.config.FeignConfig;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoReissueRequest;
import com.sopt.bbangzip.fegin.kakao.dto.KakaoTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "KakaoReissueClient", url = KakaoConstant.KAKAO_TOKEN_URL, configuration = FeignConfig.class)
public interface KakaoReissueClient {

    @PostMapping
    KakaoTokenResponse kakaoReissue(
            final KakaoReissueRequest kakaoReissueRequest
    );
}
