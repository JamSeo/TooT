package com.realfinal.toot.api.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class OauthTokenRes {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    private String scope;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;

    @JsonProperty("id_token")
    private String idToken;

    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("error_code")
    private String errorCode;
    @Builder
    public OauthTokenRes(String accessToken, String tokenType, String scope, int expiresIn,
            String refreshToken, int refreshTokenExpiresIn, String idToken, String error,
            String errorDescription, String errorCode) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.scope = scope;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.idToken = idToken;
        this.error = error;
        this.errorDescription = errorDescription;
        this.errorCode = errorCode;
    }
}
