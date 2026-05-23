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

    // 가게 미션 생성
    @PostMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<Void> createMission(
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    // 가게 내 미션들 조회
    @GetMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.Pagnation<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ){
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId, pageSize, cursor, query));
    }

    // 미션 성공
    @PatchMapping("/v1/missions/{mission_id}")
    public ApiResponse<MissionResDTO.CompleteMission> completeMission(
            @PathVariable("mission_id") Long missionId,
            @Valid @RequestBody MissionReqDTO.CompleteMission dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.completeMission(missionId, dto));
    }

    // 내가 진행중인 미션 목록 — 오프셋 페이지네이션 (memberId는 Request Body)
    @PostMapping("/v1/missions/ongoing")
    public ApiResponse<MissionResDTO.OngoingMissionList> getOngoingMissionList(
            @Valid @RequestBody MissionReqDTO.OngoingMissionList dto
    ) {
        BaseSuccessCode code = MissionSuccessCode.LIST_OK;
        return ApiResponse.onSuccess(code, missionService.getOngoingMissionList(dto));
    }

    // 내 미션 목록 (진행중 / 완료) — 커서 페이징
    @GetMapping("/v1/missions")
    public ApiResponse<MissionResDTO.MyMissionList> getMyMissionList(
            @RequestParam Long memberId,
            @RequestParam String status,                // ONGOING | DONE
            @RequestParam(required = false) Integer cursorPoint,
            @RequestParam(required = false) Long cursorId
    ) {
        BaseSuccessCode code = MissionSuccessCode.LIST_OK;
        return ApiResponse.onSuccess(
                code,
                missionService.getMyMissionList(memberId, status, cursorPoint, cursorId)
        );
    }

    // 홈 화면: 지역 도전 가능 미션 — 커서 페이징
    @GetMapping("/v1/home/missions")
    public ApiResponse<MissionResDTO.HomeMissionList> getHomeMissionList(
            @RequestParam Long memberId,
            @RequestParam Long locationId,
            @RequestParam(required = false) Integer cursorPoint,
            @RequestParam(required = false) Long cursorId
    ) {
        BaseSuccessCode code = MissionSuccessCode.LIST_OK;
        return ApiResponse.onSuccess(
                code,
                missionService.getHomeMissionList(memberId, locationId, cursorPoint, cursorId)
        );
    }
}
