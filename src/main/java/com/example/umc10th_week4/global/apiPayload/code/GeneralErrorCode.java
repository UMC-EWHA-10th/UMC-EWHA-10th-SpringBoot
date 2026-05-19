package com.example.umc10th_week4.global.apiPayload.code;

import com.example.umc10th_week4.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GeneralErrorCode implements BaseErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL500", "서버 에러가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "GLOBAL400", "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "GLOBAL404", "요청한 리소스를 찾을 수 없습니다."),  // ← 여기 ; → ,
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401_1", "인증되지 않았습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403_1", "접근 권한이 없습니다.");              // ← 마지막만 ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}