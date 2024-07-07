package com.tourapi.mandi.global.redis.repository;

import com.tourapi.mandi.global.redis.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
    Optional<Token> findById(String refreshToken);

    Optional<Token> findByAccessToken(String accessToken);
}
