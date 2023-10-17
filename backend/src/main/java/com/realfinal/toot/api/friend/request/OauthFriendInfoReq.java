package com.realfinal.toot.api.friend.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OauthFriendInfoReq {

    private final String profile_nickname;
    private final String profile_thumbnail_image;
    private final Boolean favorite;
    private final Long id;

    @Builder
    public OauthFriendInfoReq(String profile_nickname, String profile_thumbnail_image,
            Boolean favorite,
            Long id) {
        this.profile_nickname = profile_nickname;
        this.profile_thumbnail_image = profile_thumbnail_image;
        this.favorite = favorite;
        this.id = id;
    }
}
