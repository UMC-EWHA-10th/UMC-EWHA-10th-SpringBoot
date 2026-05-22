package com.example.umc10th_week4.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralSuccessCode implements BaseSuccessCOde {

    SUCCESS(HttpStatus.OK, "GLOBAL200", "성공입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}