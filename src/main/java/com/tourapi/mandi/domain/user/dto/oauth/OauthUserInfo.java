package com.tourapi.mandi.domain.user.dto.oauth;


import com.tourapi.mandi.domain.user.entity.constant.Provider;

public interface OauthUserInfo {
    String email();

    Provider provider();

    String id();
}
