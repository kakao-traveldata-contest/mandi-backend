package com.tourapi.mandi.domain.user.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Provider {

    PROVIDER_GOOGLE("google", "구글"),
    PROVIDER_KAKAO("kakao", "카카오");


    @Getter
    private final String provider;

    @Getter
    private final String description;
}
