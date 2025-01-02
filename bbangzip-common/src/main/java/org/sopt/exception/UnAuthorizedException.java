package org.sopt.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.sopt.code.ErrorCode;

@Getter
@RequiredArgsConstructor
public class UnAuthorizedException extends RuntimeException{
    private final ErrorCode errorCode;
}
