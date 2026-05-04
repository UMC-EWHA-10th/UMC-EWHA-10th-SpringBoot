package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    // 미션 성공
    @PatchMapping("/v1/missions/{mission_id}")
    public ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable("mission_id") Long missionId,
            @Valid @RequestBody MissionReqDTO.CompleteMission dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.completeMission(missionId, dto));
    }

    // 미션 목록 조회
    @GetMapping("/v1/missions")
    public ApiResponse<MissionResDTO.MissionList> getMissionList(
            @RequestParam String status     // ONGOING | DONE
    ) {
        BaseSuccessCode code = MissionSuccessCode.LIST_OK;
        return ApiResponse.onSuccess(code, missionService.getMissionList(status));
    }
}
