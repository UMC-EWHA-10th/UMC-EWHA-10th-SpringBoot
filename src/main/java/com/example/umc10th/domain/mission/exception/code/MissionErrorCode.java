package com.example.umc10th.domain.mission.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionErrorCode {
    // 미션 조회
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_1", "해당 미션을 찾을 수 없습니다."),
    MEMBER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION404_2", "유저의 미션 수행 내역을 찾을 수 없습니다."),

    //미션 성공 누르기
    ALREADY_COMPLETED_MISSION(HttpStatus.BAD_REQUEST, "MISSION400_1", "이미 완료된 미션입니다."),
    NOT_ONGOING_MISSION(HttpStatus.BAD_REQUEST, "MISSION400_2", "진행 중인 미션이 아닙니다."),
    MISSION_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "MISSION403_1", "해당 미션을 수행할 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

