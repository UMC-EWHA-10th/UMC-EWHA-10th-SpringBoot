package com.example.umc10th.domain.member.entity;

import com.example.umc10th.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 마이페이지 닉네임
    @Column(nullable = false, length = 20)
    private String nickname;

    // 이메일
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    // 비밀번호
    @Column(nullable = false, length = 100)
    private String password;

    // 휴대폰 번호
    @Column(length = 20)
    private String phoneNumber;

    // 포인트
    @Builder.Default
    @Column(nullable = false)
    private Integer point = 0;
}