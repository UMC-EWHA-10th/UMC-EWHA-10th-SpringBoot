package com.example.umc10th.domain.mission.entity;

import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예: 10,000원 이상의 식사를 하세요!
    @Column(nullable = false, length = 100)
    private String missionSpec;

    // 예: 500
    @Column(nullable = false)
    private Integer reward;

    // 예: 7 → D-7 표시용
    @Column(nullable = false)
    private Integer deadline;

    // 어느 가게의 미션인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}