package com.sopt.bbangzip.test;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.token.api.JwtTokensDto;
import com.sopt.bbangzip.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/success")
    public ResponseEntity<TestDto> testSuccess(){
        return ResponseEntity.ok(TestDto.builder().content("얼른 자고싶어..").build());
    }

    @GetMapping("/token/{userId}")
    public ResponseEntity<JwtTokensDto> testToken(
            @PathVariable final Long userId
    ) {
        JwtTokensDto tokens = jwtTokenProvider.issueTokens(userId);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/security")
    public ResponseEntity<TestDto> testSecurity(
            @UserId final Long userId,
            @Valid @RequestBody final TestSecurity testSecurity
    ) {
        return ResponseEntity.ok(TestDto.builder().content(testSecurity.name() + " " + userId).build());
    }
}
