package com.sopt.bbangzip.domain.auth;

import com.sopt.bbangzip.common.annotation.UserId;
import com.sopt.bbangzip.domain.token.api.JwtTokensDto;
import com.sopt.bbangzip.domain.token.api.ReissueJwtTokensDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    // 소셜 로그인 API
    @PostMapping("/user/auth/signin")
    public ResponseEntity<JwtTokensDto> kakaoLogin(
            @RequestParam final String code
    ){
        return ResponseEntity.ok(authService.kakaoLogin(code));
    }

    // 리프레시 토큰 재발급 API
    @PostMapping("/user/auth/re-issue")
    public ResponseEntity<ReissueJwtTokensDto> reissueToken(
            @UserId final long userId
    ){
        return ResponseEntity.ok(authService.reissueToken(userId));
    }

    // 로그아웃 API (진행중)
    @DeleteMapping("/user/auth/siginout")
    public ResponseEntity<Void> logout(
            @UserId final long userId
    )
    {
        authService.logout(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 회원 탈퇴 API
     * 일단 임시니까 나중에 바꾸세용가리 (진행중)
     */
    @DeleteMapping("/user/auth/withdraw")
    public ResponseEntity<Void> withdraw(
            @UserId final long userId
    ){
        return  ResponseEntity.noContent().build();
    }
}