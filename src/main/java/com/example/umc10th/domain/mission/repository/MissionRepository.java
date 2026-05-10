package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(value= "SELECT m FROM Mission m "+
            "JOIN FETCH m.store s "+
            "LEFT JOIN MemberMission mm ON mm.mission = m AND mm.member=:member "+
            "WHERE s.location.id = :locationId "+
            "AND mm.id IS NULL " +
            "AND (:cursor IS NULL OR m.id < :cursor) "+
            "ORDER BY m.id DESC",
            countQuery = "SELECT COUNT(m) FROM Mission m " +
                    "JOIN m.store s " +
                    "LEFT JOIN MemberMission mm ON mm.mission = m AND mm.member = :member " +
                    "WHERE s.location.id = :locationId AND mm.id IS NULL")
    Page<Mission> findHomeMissionList(@Param("member") Member member, @Param("locationId") Long locationId, @Param("cursor") Long lastId, Pageable pageable);
}
