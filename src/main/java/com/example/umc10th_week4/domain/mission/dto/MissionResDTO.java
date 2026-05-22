package com.example.umc10th_week4.domain.mission.dto;

import com.example.umc10th_week4.domain.mission.enums.MissionStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class MissionResDTO {

    // 내 미션 목록 한 건
    @Getter
    @Builder
    public static class MyMissionResponse {
        private Long userMissionId;
        private String storeName;
        private String missionTitle;
        private Integer rewardPoint;
        private MissionStatus status;
        private LocalDate deadline;
    }

    // 페이징 감싸는 DTO
    @Getter
    @Builder
    public static class MyMissionListResponse {
        private List<MyMissionResponse> missionList;
        private int currentPage;
        private int totalPages;
        private boolean isFirst;
        private boolean isLast;
    }

    // 홈 화면 미션 한 건
    @Getter
    @Builder
    public static class HomeMissionResponse {
        private Long missionId;
        private String storeName;
        private String missionTitle;
        private Integer rewardPoint;
        private LocalDate deadline;
    }

    // 홈 화면 페이징
    @Getter
    @Builder
    public static class HomeMissionListResponse {
        private List<HomeMissionResponse> missionList;
        private int currentPage;
        private int totalPages;
        private boolean isFirst;
        private boolean isLast;
    }
}