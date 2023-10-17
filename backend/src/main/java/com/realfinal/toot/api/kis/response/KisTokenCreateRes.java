package com.realfinal.toot.api.kis.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KisTokenCreateRes {
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("access_token_token_expired")
  private String expired;
  @JsonProperty("token_type")
  private String type;
  @JsonProperty("expires_in")
  private long expiresIn;
}