package com.sopt.bbangzip.common.advice;

import com.sopt.bbangzip.common.exception.base.BusinessException;
import com.sopt.bbangzip.common.exception.base.ForbiddenException;
import com.sopt.bbangzip.common.exception.base.NotfoundException;
import com.sopt.bbangzip.common.exception.base.UnAuthorizedException;
import com.sopt.bbangzip.common.exception.code.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 요청은 정상이나 비즈니스 로직 상에서 실패가 있는 경우
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorCode> handleBusinessException(BusinessException e) {
        log.error("GlobalExceptionHandler catch BusinessException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    // 권한이 없는 경우
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorCode> handleException(ForbiddenException e) {
        log.error("handleException() in GlobalExceptionHandler throw ForbiddenException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.FORBIDDEN.getHttpStatus())
                .body(ErrorCode.FORBIDDEN);
    }

    // DB 에서 데이터를 찾지 못한 경우
    @ExceptionHandler(NotfoundException.class)
    public ResponseEntity<ErrorCode> handleNotFoundException(NotfoundException e){
        log.error("GlobalExceptionHandler catch NotFoundException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    // 인증되지 않은 사용자인 경우
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorCode> handleException(UnAuthorizedException e) {
        log.error(e.getErrorCode().getMessage(), e);
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    // 기본 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCode> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    // 유효하지 않은 인자가 들어온 경우 (@Valid 사용 시 수행)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorCode> handleException(MethodArgumentNotValidException e) {
        log.error("handleException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INVALID_ARGUMENTS.getHttpStatus())
                .body(ErrorCode.INVALID_ARGUMENTS);
    }

    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler(value={NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorCode> handleNoPageFoundException(Exception e) {
        log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.NOT_FOUND_END_POINT.getHttpStatus())
                .body(ErrorCode.NOT_FOUND_END_POINT);
    }
}
