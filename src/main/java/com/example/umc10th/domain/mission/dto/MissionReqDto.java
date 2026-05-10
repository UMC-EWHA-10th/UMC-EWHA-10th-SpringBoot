package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;

public class MissionReqDto {
    // 홈 화면
    public record MissionSummary(
            Long locationId,
            Long lastId, // 마지막으로 본 미션 id
            Integer size // 한 번에 가져올 개수
    ){}

    // 미션 목록 조회
    public record GetMissionList(
        MissionStatus status,
        Long lastId,
        Integer size
    ){}

    // 진행 중인 미션 목록 조회
    public record GetOngoingMissionList(Long memberId,
       Integer page, //몇 번째 페이지인지
       Integer size //한 페이지에 담길 데이터 개수
    ){}
}

