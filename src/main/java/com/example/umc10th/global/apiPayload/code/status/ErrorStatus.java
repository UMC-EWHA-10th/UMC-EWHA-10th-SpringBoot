package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR("COMMON500", "서버 에러입니다."),
    _BAD_REQUEST("COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED("COMMON401", "인증이 필요합니다."),
    _FORBIDDEN("COMMON403", "접근 권한이 없습니다."),

    MEMBER_EMAIL_ALREADY_EXISTS("MEMBER400_1", "이미 존재하는 이메일입니다.");

    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReasonDTO() {
        return ReasonDTO.builder()
                .code(code)
                .message(message)
                .build();
    }
}