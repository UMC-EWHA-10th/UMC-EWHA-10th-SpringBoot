package com.example.umc10th.global.apiPayload.code;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReasonDTO {
    private String code;
    private String message;
}