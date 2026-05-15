package com.example.chap4.dto;

import lombok.Builder;

import java.util.List;

public class MissionResponseDTO {

    @Builder
    public record MyMissionResponse(
            Long missionId,
            String title,
            String description,
            Integer rewardPoint
    ) {
    }

    @Builder
    public record MyMissionPageResponse(
            List<MyMissionResponse> data,
            Integer pageNumber,
            Integer pageSize
    ) {
    }
}
