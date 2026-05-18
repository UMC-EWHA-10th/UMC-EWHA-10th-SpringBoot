package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;

    public MissionResDTO.HomeMissionResultDTO getHomeMissions(Long locationId, Integer page) {
        int pageNumber = page == null ? 0 : page;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);

        Page<Mission> missionPage = missionRepository.findHomeMissionsByLocation(locationId, pageRequest);

        List<MissionResDTO.HomeMissionDTO> missionDTOList = missionPage.stream()
                .map(mission -> MissionResDTO.HomeMissionDTO.builder()
                        .missionId(mission.getId())
                        .storeName(mission.getStore().getName())
                        .missionSpec(mission.getMissionSpec())
                        .reward(mission.getReward())
                        .deadline(mission.getDeadline())
                        .build())
                .toList();

        return MissionResDTO.HomeMissionResultDTO.builder()
                .missionList(missionDTOList)
                .listSize(missionDTOList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }

    public MissionResDTO.MyMissionResultDTO getMyProgressMissions(Long memberId, Integer page) {
        int pageNumber = page == null ? 0 : page;
        PageRequest pageRequest = PageRequest.of(pageNumber, 10);

        Page<MemberMission> missionPage = memberMissionRepository.findMyMissionsByStatus(
                memberId,
                MissionStatus.CHALLENGING,
                pageRequest
        );

        List<MissionResDTO.MyMissionDTO> missionDTOList = missionPage.stream()
                .map(memberMission -> MissionResDTO.MyMissionDTO.builder()
                        .memberMissionId(memberMission.getId())
                        .storeName(memberMission.getMission().getStore().getName())
                        .missionSpec(memberMission.getMission().getMissionSpec())
                        .reward(memberMission.getMission().getReward())
                        .deadline(memberMission.getMission().getDeadline())
                        .status(memberMission.getStatus().name())
                        .build())
                .toList();

        return MissionResDTO.MyMissionResultDTO.builder()
                .missionList(missionDTOList)
                .listSize(missionDTOList.size())
                .totalPage(missionPage.getTotalPages())
                .totalElements(missionPage.getTotalElements())
                .isFirst(missionPage.isFirst())
                .isLast(missionPage.isLast())
                .build();
    }
}