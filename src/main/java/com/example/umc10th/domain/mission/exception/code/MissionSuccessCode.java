package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseCode;
import com.example.umc10th.global.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionSuccessCode implements BaseCode {

    MISSION_LIST_SUCCESS("MISSION200_1", "성공적으로 미션 목록을 조회했습니다.");

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