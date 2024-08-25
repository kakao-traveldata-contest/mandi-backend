package com.tourapi.mandi.global.redis.service;
import com.tourapi.mandi.global.exception.Exception403;
import com.tourapi.mandi.global.redis.RedisExceptionStatus;
import com.tourapi.mandi.global.redis.entity.BlackListToken;
import com.tourapi.mandi.global.redis.repository.BlackListTokenRepository;
import com.tourapi.mandi.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListTokenService {

        private final BlackListTokenRepository blackListTokenRepository;

        public void save(String accessToken) {
        Long remainTime = JwtProvider.getRemainExpiration(accessToken);
        BlackListToken blackListToken = BlackListToken.builder()
                .accessToken(accessToken)
                .expiration(remainTime)
                .build();

        blackListTokenRepository.save(blackListToken);
    }

        public void validAccessToken(String accessToken) {
        if (blackListTokenRepository.existsById(accessToken)) {
            throw new Exception403(RedisExceptionStatus.BLACKLIST_TOKEN);
        }
    }

}
