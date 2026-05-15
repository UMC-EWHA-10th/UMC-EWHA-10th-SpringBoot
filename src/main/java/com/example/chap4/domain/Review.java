package com.example.chap4.domain;

import com.example.chap4.domain.Store;
import com.example.chap4.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

// Review.java
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private com.example.chap4.domain.Member member; // 누가 썼는지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store; // 어느 가게 리뷰인지
}
