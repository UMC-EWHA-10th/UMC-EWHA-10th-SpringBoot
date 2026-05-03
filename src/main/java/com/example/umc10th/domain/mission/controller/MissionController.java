package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    // 미션 성공
    @PatchMapping("/v1/missions/{mission_id}")
    public ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable("mission_id") Long missionId,
            @Valid @RequestBody MissionReqDTO.CompleteMission dto
    ) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, null); // result 추후 수정
    }

    // 미션 목록 조회
    @GetMapping("/v1/missions")
    public ApiResponse<MissionResDTO.MissionList> getMissionList(
            @RequestParam String status     // ONGOING | DONE
    ) {
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, null); // result 추후 수정
    }
}
