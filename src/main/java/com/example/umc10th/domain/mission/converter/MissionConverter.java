package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.MissionResDto;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {
    //개별 미션 엔티티 -> MissionResDto.Mission
    public static MissionResDto.Mission toMissionResDto(MemberMission memberMission) {
        return MissionResDto.Mission.builder()
                .missionId(memberMission.getMission().getId())
                .title(memberMission.getMission().getStore()+"에서")
                .content(memberMission.getMission().getContent())
                .reward(memberMission.getMission().getPoint())
                .status(memberMission.getStatus())
                .build();

    }

    //Page 객체 -> MissionResDto.MissionList
    public static MissionResDto.MissionList toMissionListDto(Page<MemberMission> missionPage){
        //리스트 변환
        List<MissionResDto.Mission> missionDtos=missionPage.getContent().stream()
                .map(MissionConverter::toMissionResDto)
                .collect(Collectors.toList());

        Long nextLastId=missionDtos.isEmpty()?null:missionDtos.get(missionDtos.size()-1).missionId();

        return MissionResDto.MissionList.builder()
                .missionList(missionDtos)
                .nextLastId(nextLastId)
                .hasMore(missionPage.hasNext())
                .build();
    }

}
