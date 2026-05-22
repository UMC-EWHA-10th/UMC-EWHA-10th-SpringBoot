package com.example.umc10th_week4.domain.mission.service;

import com.example.umc10th_week4.domain.mission.converter.MissionConverter;
import com.example.umc10th_week4.domain.mission.dto.MissionResDTO;
import com.example.umc10th_week4.domain.mission.entity.Mission;
import com.example.umc10th_week4.domain.mission.entity.UserMission;
import com.example.umc10th_week4.domain.mission.enums.MissionStatus;
import com.example.umc10th_week4.domain.mission.repository.MissionRepository;
import com.example.umc10th_week4.domain.mission.repository.UserMissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final UserMissionRepository userMissionRepository;

    // 내 미션 목록 (페이징)
    @Transactional(readOnly = true)
    public MissionResDTO.MyMissionListResponse getMyMissions(Long userId, MissionStatus status, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10); // 페이지 번호는 0부터 시작
        Page<UserMission> result = userMissionRepository.findByUserIdAndStatus(userId, status, pageRequest);
        return MissionConverter.toMyMissionListResponse(result);
    }

    // 홈 화면 미션 목록 (지역 + 페이징)
    @Transactional(readOnly = true)
    public MissionResDTO.HomeMissionListResponse getHomeMissions(String address, int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<Mission> result = missionRepository.findMissionsByStoreAddress(address, pageRequest);
        return MissionConverter.toHomeMissionListResponse(result);
    }
}