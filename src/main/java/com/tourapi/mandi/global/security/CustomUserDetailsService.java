package com.tourapi.mandi.global.security;

import com.tourapi.mandi.domain.user.UserExceptionStatus;
import com.tourapi.mandi.domain.user.repository.UserJpaRepository;
import com.tourapi.mandi.global.exception.Exception404;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new Exception404(UserExceptionStatus.USER_NOT_FOUND)));
    }
}
