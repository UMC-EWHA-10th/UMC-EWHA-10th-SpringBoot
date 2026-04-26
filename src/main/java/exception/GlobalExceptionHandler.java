package exception;

import apiPayload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. 일반적인 Exception 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllException(Exception e) {
        ApiResponse<String> response = ApiResponse.onFailure("COMMON500", "서버 에러가 발생했습니다.", e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    // 2. 특정 예외(예: 인자값이 잘못됨) 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponse<String> response = ApiResponse.onFailure("COMMON400", "잘못된 요청입니다.", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

