package com.tourapi.mandi.global.redis.entity;


import com.tourapi.mandi.domain.user.entity.User;
import com.tourapi.mandi.global.security.JwtProvider;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash(value = "token")
public class Token {
    // Jakarta(하이버네이트) Id가 아닌 Springframework Id를 사용해야 합니다.
    @Id
    private String refreshToken;

    private Long userId;

    private String email;

    @Indexed
    private String accessToken;

    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private Long expiraition = JwtProvider.REFRESH_EXP;

    @Builder
    public Token(String refreshToken, Long userId, String email, String accessToken) {
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static Token of(String refreshToken, String accessToken, User user) {
        return Token.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Token that = (Token)obj;
        return Objects.equals(getRefreshToken(), that.getRefreshToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRefreshToken());
    }
}
