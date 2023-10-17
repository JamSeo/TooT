package com.realfinal.toot.api.user.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserReq {

    private final String providerId;
    private final String name;
    private final String profileImage;

    @Builder
    public UserReq(String providerId, String name, String profileImage) {
        this.providerId = providerId;
        this.name = name;
        this.profileImage = profileImage;
    }
}

