package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import com.example.umc10th.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR("COMMON500", "서버 에러입니다."),
    _BAD_REQUEST("COMMON400", "잘못된 요청입니다.");

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