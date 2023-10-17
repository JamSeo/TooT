package com.realfinal.toot.api.user.service;

import com.realfinal.toot.api.user.response.UserRes;

public interface UserService {

    String login(String code, String provider);

    void logout(String refreshToken);

    UserRes getUserInfo(String accessToken);

    String recreateAccessToken(String refreshToken);

    void saveTokens(String id, String refreshJWTToken, String accessToken);

    Boolean isLogout(String refreshToken);

    Boolean isUser(Long userId);
}
