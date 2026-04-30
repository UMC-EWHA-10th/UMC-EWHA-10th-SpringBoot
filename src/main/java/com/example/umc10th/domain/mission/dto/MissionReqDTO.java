package com.example.umc10th.domain.mission.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MissionReqDTO {

    // 미션 성공 처리
    public record CompleteMission(
            @NotBlank
            String status   // "COMPLETED"
    ) {}
}
