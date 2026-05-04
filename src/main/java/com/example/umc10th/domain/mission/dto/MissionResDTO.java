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

    // 내 미션 목록 1건
    @Builder
    public record MyMissionPreview(
            Long missionId,
            String storeName,
            String missionCondition,
            Integer rewardPoint,
            String missionStatus      // ONGOING | DONE
    ) {}

    // 내 미션 목록 응답 (커서 페이징)
    @Builder
    public record MyMissionList(
            List<MyMissionPreview> missions,
            Integer nextCursorPoint,
            Long nextCursorId,
            boolean hasNext
    ) {}

    // 홈 화면 미션 1건
    @Builder
    public record HomeMissionPreview(
            Long missionId,
            String storeName,
            String regionName,
            String missionCondition,
            Integer rewardPoint,
            LocalDate deadline,
            String missionStatus      // 항상 NONE (도전 가능)
    ) {}

    // 홈 화면 미션 응답 (커서 페이징)
    @Builder
    public record HomeMissionList(
            List<HomeMissionPreview> missions,
            Integer nextCursorPoint,
            Long nextCursorId,
            boolean hasNext
    ) {}

    // (기존) 미션 목록 응답 — 호환용. 신규는 MyMissionList 사용.
    @Builder
    public record MissionList(
            List<MyMissionPreview> missions
    ) {}
}
