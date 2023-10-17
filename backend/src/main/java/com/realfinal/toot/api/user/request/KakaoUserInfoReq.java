package com.realfinal.toot.api.user.request;

import java.util.Map;

public class KakaoUserInfoReq {

    private final Map<String, Object> attributes;

    public KakaoUserInfoReq(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    public String getNickName() {
        return (String) getProfile().get("nickname");
    }


    public String getImageUrl() {
        return (String)getProfile().get("profile_image_url");
    }

    public Map<String, Object> getKakaoAccount(){
        return(Map<String, Object>) attributes.get("kakao_account");
    }

    public Map<String, Object> getProfile(){
        return (Map<String, Object>) getKakaoAccount().get("profile");
    }
}