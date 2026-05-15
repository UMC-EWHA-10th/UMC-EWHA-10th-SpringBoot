package com.example.chap4.domain.repository;

import com.example.chap4.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByMember_IdAndIdLessThanOrderByIdDesc(
            Long memberId,
            Long cursor,
            Pageable pageable
    );

    Page<Review> findByMember_IdAndIdLessThanOrderByRatingDescIdDesc(
            Long memberId,
            Long cursor,
            Pageable pageable
    );
}
