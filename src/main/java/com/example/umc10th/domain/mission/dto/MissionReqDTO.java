package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import lombok.Getter;

public class MissionReqDTO {

    @Getter
    public static class HomeMissionRequestDTO {
        private Long locationId;
        private Integer page;
    }

    @Getter
    public static class MyMissionRequestDTO {
        private Long memberId;
        private MissionStatus status;
        private Integer page;
    }
}