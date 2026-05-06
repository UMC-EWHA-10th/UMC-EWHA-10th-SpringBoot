package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {

    // 내가 진행중/진행완료한 미션 목록 조회
    @Query("""
            SELECT mm
            FROM MemberMission mm
            JOIN mm.mission m
            JOIN m.store s
            WHERE mm.member.id = :memberId
            AND mm.status = :status
            ORDER BY mm.createdAt DESC
            """)
    Page<MemberMission> findMyMissionsByStatus(
            @Param("memberId") Long memberId,
            @Param("status") MissionStatus status,
            Pageable pageable
    );
}