package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 내가 작성한 리뷰 조회 - ID 순 커서 기반
    @Query("""
            SELECT r
            FROM Review r
            JOIN FETCH r.store s
            WHERE r.member.id = :memberId
            AND (:cursorId IS NULL OR r.id < :cursorId)
            ORDER BY r.id DESC
            """)
    List<Review> findMyReviewsByIdCursor(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    // 내가 작성한 리뷰 조회 - 별점 순 커서 기반
    @Query("""
            SELECT r
            FROM Review r
            JOIN FETCH r.store s
            WHERE r.member.id = :memberId
            AND (
                :cursorScore IS NULL
                OR r.score < :cursorScore
                OR (r.score = :cursorScore AND r.id < :cursorId)
            )
            ORDER BY r.score DESC, r.id DESC
            """)
    List<Review> findMyReviewsByScoreCursor(
            @Param("memberId") Long memberId,
            @Param("cursorScore") Float cursorScore,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}