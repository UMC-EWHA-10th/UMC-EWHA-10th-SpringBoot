package com.example.umc10th_week4.domain.mission.repository;

import com.example.umc10th_week4.domain.mission.entity.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    // 홈화면: 특정 지역(address) 가게의 미션 목록 (페이징)
    @Query("SELECT m FROM Mission m JOIN m.store s WHERE s.address LIKE %:address%")
    Page<Mission> findMissionsByStoreAddress(@Param("address") String address, Pageable pageable);
}