package com.example.umc10th.domain.member.entity;

import com.example.umc10th.domain.member.entity.mapping.MemberTerm;
import com.example.umc10th.domain.member.enums.TermName;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private TermName name;

    @Builder.Default
    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberTerm> memberTermList = new ArrayList<>();
}
