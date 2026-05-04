package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;

import java.time.LocalDateTime;
import java.util.List;

public class MissionConverter {

    public static MissionResDTO.CompleteMission toCompleteMission(MemberMission memberMission, String status) {
        return MissionResDTO.CompleteMission.builder()
                .missionId(memberMission.getMission().getId())
                .status(status)
                .completedAt(LocalDateTime.now())
                .build();
    }

    public static MissionResDTO.MissionList toMissionList(List<Mission> missions, String status) {
        List<MissionResDTO.MissionPreview> previews = missions.stream()
                .map(m -> MissionResDTO.MissionPreview.builder()
                        .missionId(m.getId())
                        .storeId(m.getStore().getId())
                        .storeName(m.getStore().getName())
                        .reward(m.getPoint())
                        .deadline(m.getDeadline())
                        .missionSpec(m.getConditional())
                        .status(status)
                        .build())
                .toList();
        return MissionResDTO.MissionList.builder()
                .missions(previews)
                .build();
    }
}
