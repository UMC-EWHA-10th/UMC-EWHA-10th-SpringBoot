package com.example.umc10th.domain.review.entity;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 별점
    @Column(nullable = false)
    private Float score;

    // 리뷰 내용
    @Column(nullable = false, length = 500)
    private String body;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 어느 가게 리뷰인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}