package com.tourapi.mandi.global.redis.service;


import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.exception.Exception404;
import com.tourapi.mandi.global.redis.RedisExceptionStatus;
import com.tourapi.mandi.global.redis.entity.Token;
import com.tourapi.mandi.global.redis.repository.TokenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void save(String refreshToken, String accessToken, User user) {
        Token token = Token.of(refreshToken, accessToken, user);
        tokenRepository.save(token);
    }

    public void deleteById(String refreshToken) {
        tokenRepository.deleteById(refreshToken);
    }

    public void deleteByAccessToken(String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken).orElseThrow(
                () -> new Exception404(RedisExceptionStatus.REFRESH_TOKEN_NOT_FOUND));

        tokenRepository.deleteById(token.getRefreshToken());
    }

    public boolean existsById(String refreshToken) {
        return tokenRepository.existsById(refreshToken);
    }
}
