package com.example.chap4.service;

import com.example.chap4.domain.UserMission;
import com.example.chap4.domain.mission.repository.UserMissionRepository;
import com.example.chap4.dto.MissionRequestDTO;
import com.example.chap4.dto.MissionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final UserMissionRepository userMissionRepository;

    public MissionResponseDTO.MyMissionPageResponse getMyMissions(
            MissionRequestDTO.MyMissionRequest request
    ) {
        Pageable pageable = PageRequest.of(
                request.pageNumber(),
                request.pageSize(),
                Sort.by("id").descending()
        );

        Page<UserMission> missionPage =
                userMissionRepository.findAllByMember_IdAndStatus(
                        request.memberId(),
                        "진행중",
                        pageable
                );

        List<MissionResponseDTO.MyMissionResponse> missionList =
                missionPage.getContent()
                        .stream()
                        .map(userMission -> MissionResponseDTO.MyMissionResponse.builder()
                                .missionId(userMission.getMission().getId())
                                .title(userMission.getMission().getTitle())
                                .description(userMission.getMission().getDescription())
                                .rewardPoint(userMission.getMission().getRewardPoint())
                                .build())
                        .toList();

        return MissionResponseDTO.MyMissionPageResponse.builder()
                .data(missionList)
                .pageNumber(missionPage.getNumber())
                .pageSize(missionPage.getSize())
                .build();
    }
}