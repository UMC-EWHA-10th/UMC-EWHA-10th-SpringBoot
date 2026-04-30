package com.example.umc10th_week4.global.apiPayload.code;

import org.springframework.http.HttpStatus;

public interface BaseSuccessCOde {
    HttpStatus getStatus();
    String getCode();
    String getMessage();
}