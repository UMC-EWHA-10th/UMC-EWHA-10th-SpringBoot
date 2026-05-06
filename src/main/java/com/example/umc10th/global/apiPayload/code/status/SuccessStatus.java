package com.example.umc10th.global.apiPayload.code.status;

import com.example.umc10th.global.apiPayload.code.BaseCode;
import com.example.umc10th.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    _OK("COMMON200", "성공입니다.");

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