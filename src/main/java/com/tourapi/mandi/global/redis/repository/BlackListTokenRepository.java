package com.tourapi.mandi.global.redis.repository;

import com.tourapi.mandi.global.redis.entity.BlackListToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BlackListTokenRepository  extends CrudRepository<BlackListToken, String> {
    Optional<BlackListToken> findById(String accessToken);
}
