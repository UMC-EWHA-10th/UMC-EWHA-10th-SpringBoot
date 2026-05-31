package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * 내가 작성한 리뷰 목록 — ID 내림차순 (최신순) 커서 페이징
     * 첫 페이지: cursorId = Long.MAX_VALUE 전달
     */
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store s " +
            "WHERE r.member.id = :memberId " +
            "AND r.id < :cursorId " +
            "ORDER BY r.id DESC")
    List<Review> findMyReviewsByIdCursor(
            @Param("memberId") Long memberId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    /**
     * 내가 작성한 리뷰 목록 — 별점 내림차순 (동점이면 ID 내림차순) 커서 페이징
     * 첫 페이지: cursorStar = 5.01, cursorId = Long.MAX_VALUE 전달
     */
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.store s " +
            "WHERE r.member.id = :memberId " +
            "AND (r.star < :cursorStar " +
            "     OR (r.star = :cursorStar AND r.id < :cursorId)) " +
            "ORDER BY r.star DESC, r.id DESC")
    List<Review> findMyReviewsByStarCursor(
            @Param("memberId") Long memberId,
            @Param("cursorStar") BigDecimal cursorStar,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
