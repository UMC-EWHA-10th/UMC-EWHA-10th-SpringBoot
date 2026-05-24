package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
    @Query(value="SELECT mm FROM MemberMission mm "+
            "JOIN FETCH mm.mission m "+
            "JOIN FETCH m.store s "+
            "WHERE mm.member=:member AND mm.status=:status " +
            "AND (:cursor IS NULL OR m.id < :cursor) "+
            "ORDER BY m.id DESC",
            countQuery = "SELECT COUNT(mm) FROM MemberMission mm "+
                    "WHERE mm.member=:member AND mm.status=:status ")
    Page<MemberMission> findAllByMemberAndStatus(@Param("member") Member member, @Param("status") String status, @Param("cursor") Long lastId, Pageable pageable);


    @Query("SELECT COUNT(mm) FROM MemberMission mm "+
        "JOIN mm.mission m "+
        "WHERE m.store.location.id=:locationId "+
        "AND mm.member=:member "+
        "AND mm.status=:status ")
    Integer countCompletedMissionByLocationId(@Param("member") Member member, @Param("status") MissionStatus status, @Param("locationId") Long locationId);

    @Query(value="SELECT mm FROM MemberMission mm "+
        "JOIN FETCH mm.mission m "+
        "JOIN FETCH m.store s "+
        "WHERE mm.member=:member AND mm.status=:status "+
        "ORDER BY mm.createdAt DESC",
        countQuery = "SELECT COUNT(mm) FROM MemberMission mm "+
                    "WHERE mm.member=:member AND mm.status=:status") // 전체 페이지 수 계산
    Page<MemberMission> findAllByMemberAndOngoing(@Param("member") Member member, @Param("status") MissionStatus status, Pageable pageable);
}

