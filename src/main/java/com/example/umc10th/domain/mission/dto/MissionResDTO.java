package com.example.umc10th.domain.mission.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MissionResDTO {

    // 미션 성공 처리 결과
    @Builder
    public record CompleteMission(
            Long missionId,
            String status,
            LocalDateTime completedAt
    ) {}

    // 미션 목록의 1건 (요약)
    @Builder
    public record MissionPreview(
            Long missionId,
            Long storeId,
            String storeName,
            Integer reward,
            LocalDate deadline,
            String missionSpec,
            String status      // ONGOING | DONE
    ) {}

    // 미션 목록 응답
    @Builder
    public record MissionList(
            List<MissionPreview> missions
    ) {}
}
