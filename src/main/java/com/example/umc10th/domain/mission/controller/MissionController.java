package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/missions")
public class MissionController {

    private final MissionService missionService;

    // 홈 화면: 현재 선택 지역에서 도전 가능한 미션 목록
    @PostMapping("/home")
    public ApiResponse<MissionResDTO.HomeMissionResultDTO> getHomeMissions(
            @RequestBody MissionReqDTO.HomeMissionRequestDTO request
    ) {
        MissionResDTO.HomeMissionResultDTO result =
                missionService.getHomeMissions(request.getLocationId(), request.getPage());

        return ApiResponse.of(MissionSuccessCode.MISSION_LIST_SUCCESS, result);
    }

    // 내가 진행중/진행완료한 미션 목록
    @PostMapping("/my")
    public ApiResponse<MissionResDTO.MyMissionResultDTO> getMyMissions(
            @RequestBody MissionReqDTO.MyMissionRequestDTO request
    ) {
        MissionResDTO.MyMissionResultDTO result =
                missionService.getMyMissions(request.getMemberId(), request.getStatus(), request.getPage());

        return ApiResponse.of(MissionSuccessCode.MISSION_LIST_SUCCESS, result);
    }
}