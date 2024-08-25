package com.tourapi.mandi.domain.user.repository;



import com.tourapi.mandi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /**
     * 유효성 검사 - 중복 체크
     *
     * @param nickname 회원 닉네임
     * @return 닉네임 존재 여부
     */
    boolean existsByNickname(String nickname);


}
