package com.sopt.bbangzip.common.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

    /*
    컨트롤러에서 JWT의 Access Token에 저장된 userId를 매개변수로
    편리하게 주입 받을 수 있도록 도와주는 커스텀 어노테이션
    */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal(expression = "T(com.sopt.bbangzip.security.utils.SecurityUtils).checkPrincipal(#this)")
public @interface UserId {
    /*
    런타임에 SecurityUtils 에 있는 checkPrincipal 메서드를 호출한다.
    우리는 Null 검사(로그인 하지 않고 API 호출할 때 NPE) 할 필요 없이, 깔끔하게 접근 가능!
     */
}
