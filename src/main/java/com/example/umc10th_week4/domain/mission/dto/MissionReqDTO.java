package com.example.umc10th_week4.domain.mission.dto;

public class MissionReqDTO {

    public record MyMissionRequest(
            Long userId,
            Integer page
    ) {}
}