package com.example.chap4.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

public class MissionRequestDTO {

    public record MyMissionRequest(
            @NotNull(message = "멤버 ID는 필수입니다.")
            Long memberId,

            @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
            Integer pageNumber,

            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            Integer pageSize
    ) {
    }

    @Builder
    public record MyMissionResponse(
            Long missionId,
            String missionName,
            Integer reward
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