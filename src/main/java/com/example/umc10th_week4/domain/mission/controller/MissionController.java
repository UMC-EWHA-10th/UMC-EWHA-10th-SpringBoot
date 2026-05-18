package com.example.umc10th_week4.domain.mission.controller;

import com.example.umc10th_week4.domain.mission.dto.MissionReqDTO;
import com.example.umc10th_week4.domain.mission.dto.MissionResDTO;
import com.example.umc10th_week4.domain.mission.enums.MissionStatus;
import com.example.umc10th_week4.domain.mission.service.MissionService;
import com.example.umc10th_week4.global.apiPayload.ApiResponse;
import com.example.umc10th_week4.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    // 내 미션 목록 (진행중 or 완료)
    @GetMapping("/my")
    public ApiResponse<MissionResDTO.MyMissionListResponse> getMyMissions(
            @RequestBody MissionReqDTO.MyMissionRequest request,
            @RequestParam MissionStatus status
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS,
                missionService.getMyMissions(request.userId(), status, request.page() != null ? request.page() : 1));
    }

    // 홈 화면 - 지역별 미션 목록
    @GetMapping("/home")
    public ApiResponse<MissionResDTO.HomeMissionListResponse> getHomeMissions(
            @RequestParam String address,
            @RequestParam(defaultValue = "1") int page
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS,
                missionService.getHomeMissions(address, page));
    }
}