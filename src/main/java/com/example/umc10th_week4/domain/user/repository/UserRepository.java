package com.example.umc10th_week4.domain.user.repository;

import com.example.umc10th_week4.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository<엔티티, PK타입> 상속하면 끝!
}