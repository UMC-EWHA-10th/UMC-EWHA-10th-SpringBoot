package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //별점순(커서)
    @Query("""
            SELECT r From Review r 
            WHERE r.member=:member
            AND(
                        (:lastStarRating IS NULL OR r.starRating<:lastStarRating) 
                        OR(r.starRating=:lastStarRating AND r.id<:lastId)
                                    ) 
            ORDER BY r.starRating DESC, r.id DESC
                        """)
    Page<Review> findAllByMemberAndStarRatingCursor(Member member, Integer lastStarRating, Long lastId, Pageable pageable);

    //ID순(커서)
    @Query("SELECT r FROM Review r "+
            "WHERE r.member=:member "+
            "AND(:lastId IS NULL OR r.id<:lastId) "+
            "ORDER BY r.id DESC")
    Page<Review> findAllByMemberAndIdCursor(Member member, Long lastId, Pageable pageable);
}
