package com.tourapi.mandi.domain.user.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tourapi.mandi.domain.user.entity.constant.Provider;

//equals, hashcode, tostring 메서드와 private final field들, public constructor는 Java 컴파일러에 의해 생성
public record GoogleUserInfo(
        String id,
        String email,
        @JsonProperty("verified_email")
        Boolean verifiedEmail,
        String picture
)
 implements OauthUserInfo {

    @Override
    public Provider provider() {
        return Provider.PROVIDER_GOOGLE;
    }
}

