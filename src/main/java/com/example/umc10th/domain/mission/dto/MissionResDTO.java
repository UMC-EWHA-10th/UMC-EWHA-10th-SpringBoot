package com.example.umc10th.domain.mission.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MissionResDTO {

    @Getter
    @Builder
    public static class HomeMissionResultDTO {
        private List<HomeMissionDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @Getter
    @Builder
    public static class HomeMissionDTO {
        private Long missionId;
        private String storeName;
        private String missionSpec;
        private Integer reward;
        private Integer deadline;
    }

    @Getter
    @Builder
    public static class MyMissionResultDTO {
        private List<MyMissionDTO> missionList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @Getter
    @Builder
    public static class MyMissionDTO {
        private Long memberMissionId;
        private String storeName;
        private String missionSpec;
        private Integer reward;
        private Integer deadline;
        private String status;
    }
}