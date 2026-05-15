package com.example.chap4.domain;

import com.example.chap4.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// Store.java
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<com.example.chap4.domain.Mission> missionList = new ArrayList<>();
}

