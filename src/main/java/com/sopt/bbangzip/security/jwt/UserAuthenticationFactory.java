package com.sopt.bbangzip.security.jwt;

import com.sopt.bbangzip.common.constants.AuthConstant;
import com.sopt.bbangzip.security.filter.UserAuthentication;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationFactory {

    public void authenticateUser(Claims claims, HttpServletRequest request) {
        Long userId = claims.get(AuthConstant.USER_ID_CLAIM_NAME, Long.class);

        UserAuthentication authentication = UserAuthentication.createUserAuthentication(userId);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}