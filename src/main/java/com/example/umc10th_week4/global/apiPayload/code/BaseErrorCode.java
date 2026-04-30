package com.example.umc10th_week4.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getStatus();   // HTTP 상태코드
    String getCode();         // 에러 코드 문자열
    String getMessage();      // 에러 메시지
}