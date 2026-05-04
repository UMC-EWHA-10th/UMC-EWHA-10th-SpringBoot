package com.example.umc10th_week4.domain.mission.repository;

import com.example.umc10th_week4.domain.mission.entity.UserMission;
import com.example.umc10th_week4.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    // 내 미션 목록: userId + 상태 기반 페이징 조회
    Page<UserMission> findByUserIdAndStatus(Long userId, MissionStatus status, Pageable pageable);
}