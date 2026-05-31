package com.example.umc10th_week4.domain.user.repository;

import com.example.umc10th_week4.domain.user.entity.UserTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTermRepository extends JpaRepository<UserTerm, Long> {
}