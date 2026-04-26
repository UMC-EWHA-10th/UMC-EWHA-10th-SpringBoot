package com.example.umc10th.domain.mission.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MissionSuccessCode implements BaseSuccessCode {

    // 홈 화면
    MISSION_SUMMARIZED(HttpStatus.OK,
            "MISSION200_1",
            "성공적으로 홈 화면 미션 목록을 불러왔습니다."),

    // 미션 목록 조회
    MISSION_CHECKED(HttpStatus.OK,
            "MISSION200_2",
            "성공적으로 미션 목록을 불러왔습니다."),

    // 미션 성공 누르기
    MISSION_COMPLETED(HttpStatus.OK,
            "MISSION200_3",
            "성공적으로 미션이 완료되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}