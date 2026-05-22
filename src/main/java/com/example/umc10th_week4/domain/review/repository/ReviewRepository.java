package com.example.umc10th_week4.domain.review.repository;

import com.example.umc10th_week4.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // ID 순 - 처음 조회 (커서 없을 때)
    Slice<Review> findByUserMission_User_IdOrderByIdDesc(Long userId, Pageable pageable);

    // ID 순 - 커서 있을 때
    Slice<Review> findByUserMission_User_IdAndIdLessThanOrderByIdDesc(Long userId, Long cursor, Pageable pageable);

    // 별점 순 - 처음 조회 (커서 없을 때)
    Slice<Review> findByUserMission_User_IdOrderByRatingDescIdDesc(Long userId, Pageable pageable);

    // 별점 순 - 커서 있을 때 (별점이 같을 수 있어서 id도 같이 비교)
    @Query("SELECT r FROM Review r WHERE r.userMission.user.id = :userId AND (r.rating < :rating OR (r.rating = :rating AND r.id < :id)) ORDER BY r.rating DESC, r.id DESC")
    Slice<Review> findByUserIdOrderByRatingDescWithCursor(
            @Param("userId") Long userId,
            @Param("rating") Integer rating,
            @Param("id") Long id,
            Pageable pageable
    );
}