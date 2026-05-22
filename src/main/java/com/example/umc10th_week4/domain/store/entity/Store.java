package com.example.umc10th_week4.domain.store.entity;

import com.example.umc10th_week4.domain.mission.entity.Mission;
import com.example.umc10th_week4.domain.store.enums.StoreCategory;
import com.example.umc10th_week4.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "store")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private StoreCategory category;

    @Column(name = "image_url")
    private String imageUrl;

    // 연관관계: Store → Mission (양방향)
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Mission> missionList = new ArrayList<>();
}