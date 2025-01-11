package com.sopt.bbangzip.security.utils;

import com.sopt.bbangzip.common.constants.AuthConstant;
import com.sopt.bbangzip.common.exception.base.UnAuthorizedException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;

// Null check 대행할 유틸 클래스
public class SecurityUtils {

    public static Object checkPrincipal(final Object principal) {
        if (AuthConstant.ANONYMOUS_USER.equals(principal)) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED);
        }
        return principal;
    }
}