package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

public class MissionReqDTO {

    // 가게 미션 생성
    public record CreateMission(
            @NotNull(message = "마감기한은 필수입니다.")
            LocalDate deadline,
            @NotNull(message = "미션 성공 포인트는 필수입니다.")
            Integer point,
            @NotNull(message = "조건은 빈칸일 수 없습니다.")
            String conditional
    ){}

    // 가게 내 미션 조회
    @Builder
    public record GetMission(
            Long missionId,
            Integer point,
            String conditional
    ){}

    // 미션 성공 처리
    public record CompleteMission(
            @NotBlank(message = "status는 비어 있을 수 없습니다.")
            String status   // "COMPLETED"
    ) {}

    // 진행중 미션 목록 조회 (오프셋 페이지네이션, memberId는 Body로 받음)
    public record OngoingMissionList(
            @NotNull(message = "memberId는 필수입니다.")
            @Positive(message = "memberId는 양수여야 합니다.")
            Long memberId,

            @NotNull(message = "page는 필수입니다.")
            @Min(value = 0, message = "page는 0 이상이어야 합니다.")
            Integer page,

            @NotNull(message = "size는 필수입니다.")
            @Positive(message = "size는 양수여야 합니다.")
            Integer size
    ) {}
}
