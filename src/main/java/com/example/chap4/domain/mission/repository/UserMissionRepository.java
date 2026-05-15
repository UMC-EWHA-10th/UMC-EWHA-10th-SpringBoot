package com.example.chap4.domain.mission.repository;

import com.example.chap4.domain.UserMission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionRepository extends JpaRepository<UserMission, Long> {

    Page<UserMission> findAllByMember_IdAndStatus(
            Long memberId,
            String status,
            Pageable pageable
    );
}