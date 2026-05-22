package com.example.umc10th_week4.domain.mission.converter;

import com.example.umc10th_week4.domain.mission.dto.MissionResDTO;
import com.example.umc10th_week4.domain.mission.entity.Mission;
import com.example.umc10th_week4.domain.mission.entity.UserMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {

    // UserMission → 내 미션 한 건 DTO
    public static MissionResDTO.MyMissionResponse toMyMissionResponse(UserMission um) {
        return MissionResDTO.MyMissionResponse.builder()
                .userMissionId(um.getId())
                .storeName(um.getMission().getStore().getName())
                .missionTitle(um.getMission().getTitle())
                .rewardPoint(um.getMission().getRewardPoint())
                .status(um.getStatus())
                .deadline(um.getMission().getDeadline())
                .build();
    }

    // Page<UserMission> → 내 미션 목록 DTO
    public static MissionResDTO.MyMissionListResponse toMyMissionListResponse(Page<UserMission> page) {
        List<MissionResDTO.MyMissionResponse> list = page.getContent().stream()
                .map(MissionConverter::toMyMissionResponse)
                .collect(Collectors.toList());

        return MissionResDTO.MyMissionListResponse.builder()
                .missionList(list)
                .currentPage(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }

    // Mission → 홈 화면 한 건 DTO
    public static MissionResDTO.HomeMissionResponse toHomeMissionResponse(Mission m) {
        return MissionResDTO.HomeMissionResponse.builder()
                .missionId(m.getId())
                .storeName(m.getStore().getName())
                .missionTitle(m.getTitle())
                .rewardPoint(m.getRewardPoint())
                .deadline(m.getDeadline())
                .build();
    }

    // Page<Mission> → 홈 화면 목록 DTO
    public static MissionResDTO.HomeMissionListResponse toHomeMissionListResponse(Page<Mission> page) {
        List<MissionResDTO.HomeMissionResponse> list = page.getContent().stream()
                .map(MissionConverter::toHomeMissionResponse)
                .collect(Collectors.toList());

        return MissionResDTO.HomeMissionListResponse.builder()
                .missionList(list)
                .currentPage(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}