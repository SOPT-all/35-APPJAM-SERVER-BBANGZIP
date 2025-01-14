package com.sopt.bbangzip.test;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.token.api.JwtTokensDto;
import com.sopt.bbangzip.domain.user.entity.User;
import com.sopt.bbangzip.domain.user.service.UserRetriever;
import com.sopt.bbangzip.fegin.kakao.service.KakaoService;
import com.sopt.bbangzip.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final KakaoService kakaoService;
    private final UserRetriever userRetriever;

    @GetMapping("/api/v1/test/success")
    public ResponseEntity<TestDto> testSuccess(){
        return ResponseEntity.ok(TestDto.builder().content("얼른 자고싶어..").build());
    }

    @GetMapping("/api/v1/test/token/{userId}")
    public ResponseEntity<JwtTokensDto> testToken(
            @PathVariable final Long userId
    ) {
        // 유저의 온보딩 여부를 가져오는 로직 추가
        User user = userRetriever.findByUserId(userId);
        boolean isOnboardingComplete = user.getIsOnboardingComplete();
        JwtTokensDto tokens = jwtTokenProvider.issueTokens(userId, isOnboardingComplete);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/api/v1/test/security")
    public ResponseEntity<TestDto> testSecurity(
            @UserId final Long userId,
            @Valid @RequestBody final TestSecurity testSecurity
    ) {
        return ResponseEntity.ok(TestDto.builder().content(testSecurity.name() + " " + userId).build());
    }

    // 클라이언트(웹브라우저) 측에서 로그인 요청 시 코드 받아오는 테스트 컨트롤러
    @GetMapping("/callback")
    public ResponseEntity<String> callback(
            @RequestParam final String code
    ) {
        return ResponseEntity.ok("Code received: " + code);
    }
}
