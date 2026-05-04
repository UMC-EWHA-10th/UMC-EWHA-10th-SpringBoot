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

    // 내 미션 목록 (커서 페이징)
    public static MissionResDTO.MyMissionList toMyMissionList(List<MemberMission> memberMissions, int pageSize) {
        boolean hasNext = memberMissions.size() > pageSize;
        List<MemberMission> page = hasNext ? memberMissions.subList(0, pageSize) : memberMissions;

        List<MissionResDTO.MyMissionPreview> previews = page.stream()
                .map(mm -> {
                    Mission mi = mm.getMission();
                    return MissionResDTO.MyMissionPreview.builder()
                            .missionId(mi.getId())
                            .storeName(mi.getStore().getName())
                            .missionCondition(mi.getConditional())
                            .rewardPoint(mi.getPoint())
                            .missionStatus(mm.getIsComplete() ? "DONE" : "ONGOING")
                            .build();
                })
                .toList();

        Mission last = page.isEmpty() ? null : page.get(page.size() - 1).getMission();
        return MissionResDTO.MyMissionList.builder()
                .missions(previews)
                .nextCursorPoint(last != null ? last.getPoint() : null)
                .nextCursorId(last != null ? last.getId() : null)
                .hasNext(hasNext)
                .build();
    }

    // 홈 화면 미션 목록 (커서 페이징)
    public static MissionResDTO.HomeMissionList toHomeMissionList(List<Mission> missions, int pageSize) {
        boolean hasNext = missions.size() > pageSize;
        List<Mission> page = hasNext ? missions.subList(0, pageSize) : missions;

        List<MissionResDTO.HomeMissionPreview> previews = page.stream()
                .map(mi -> MissionResDTO.HomeMissionPreview.builder()
                        .missionId(mi.getId())
                        .storeName(mi.getStore().getName())
                        .regionName(mi.getStore().getLocation().getName().name())
                        .missionCondition(mi.getConditional())
                        .rewardPoint(mi.getPoint())
                        .deadline(mi.getDeadline())
                        .missionStatus("NONE")
                        .build())
                .toList();

        Mission last = page.isEmpty() ? null : page.get(page.size() - 1);
        return MissionResDTO.HomeMissionList.builder()
                .missions(previews)
                .nextCursorPoint(last != null ? last.getPoint() : null)
                .nextCursorId(last != null ? last.getId() : null)
                .hasNext(hasNext)
                .build();
    }
}
