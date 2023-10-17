package com.realfinal.toot.api.kis.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.realfinal.toot.config.KisConfig;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KisTokenCreateReq {
  @JsonProperty("grant_type")
  private String grantType = "client_credentials";
  @JsonProperty("appkey")
  private String appKey;
  @JsonProperty("appsecret")
  private String appSecret;

  public KisTokenCreateReq(KisConfig kisConfig) {
    this.appKey = kisConfig.getAppKey();
    this.appSecret = kisConfig.getAppSecret();
  }
}