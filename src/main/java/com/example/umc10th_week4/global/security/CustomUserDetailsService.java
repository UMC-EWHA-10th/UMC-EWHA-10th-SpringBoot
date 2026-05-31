package com.example.umc10th_week4.global.security;

import com.example.umc10th_week4.domain.user.entity.User;
import com.example.umc10th_week4.domain.user.repository.UserRepository;
import com.example.umc10th_week4.global.apiPayload.code.GeneralErrorCode;
import com.example.umc10th_week4.global.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ProjectException(GeneralErrorCode.NOT_FOUND));
        return new AuthMember(user);
    }
}