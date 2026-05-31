package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class MissionConverter {

    // 미션 성공 처리
    public static MissionResDTO.CompleteMission toCompleteMission(MemberMission memberMission, String status) {
        return MissionResDTO.CompleteMission.builder()
                .missionId(memberMission.getMission().getId())
                .status(status)
                .completedAt(LocalDateTime.now())
                .build();
    }

    // 가게 미션 생성
    public static Mission toMission(
            Store store,
            MissionReqDTO.CreateMission dto
    ){
        return Mission.builder()
                .store(store)
                .conditional(dto.conditional())
                .point(dto.point())
                .deadline(dto.deadline())
                .build();
    }

    // 가게 내 미션 1건 응답 변환
    public static MissionResDTO.GetMission toGetMission(Mission mission) {
        return MissionResDTO.GetMission.builder()
                .missionId(mission.getId())
                .missionCondition(mission.getConditional())
                .rewardPoint(mission.getPoint())
                .deadline(mission.getDeadline())
                .build();
    }


    // 페이지네이션 틀 생성
    public static <T> MissionResDTO.Pagnation<T> toPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {
        return MissionResDTO.Pagnation.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
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

    // 진행중 미션 목록 (오프셋 페이지네이션)
    public static MissionResDTO.OngoingMissionList toOngoingMissionList(Page<MemberMission> page) {
        List<MissionResDTO.MyMissionPreview> previews = page.getContent().stream()
                .map(mm -> {
                    Mission mi = mm.getMission();
                    return MissionResDTO.MyMissionPreview.builder()
                            .missionId(mi.getId())
                            .storeName(mi.getStore().getName())
                            .missionCondition(mi.getConditional())
                            .rewardPoint(mi.getPoint())
                            .missionStatus("ONGOING")
                            .build();
                })
                .toList();

        return MissionResDTO.OngoingMissionList.builder()
                .missions(previews)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
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
