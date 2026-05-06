package com.example.umc10th.global.apiPayload.exception;

import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    public GeneralException(BaseErrorCode code) {
        super(code.getReasonDTO().getMessage());
        this.code = code;
    }
}