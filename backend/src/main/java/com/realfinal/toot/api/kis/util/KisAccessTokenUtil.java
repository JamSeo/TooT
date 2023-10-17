package com.realfinal.toot.api.kis.util;

import static com.realfinal.toot.common.exception.ErrorCode.KIS_TOKEN_REQUEST_FAILED;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinal.toot.api.kis.request.KisTokenCreateReq;
import com.realfinal.toot.api.kis.response.KisTokenCreateRes;
import com.realfinal.toot.common.exception.kis.KisTokenRequestException;
import com.realfinal.toot.config.KisConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class KisAccessTokenUtil {

  private final String KIS_URI = "https://openapi.koreainvestment.com:9443";
  private final WebClient kisWebClient =
      WebClient
          .builder()
          .baseUrl(KIS_URI)
          .build();
  private final KisConfig kisConfig;

  private String accessToken;

  @PostConstruct
  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 자정 토큰 발급
  public void init() {

    KisTokenCreateReq kisTokenCreateReq = new KisTokenCreateReq(kisConfig);

    try{
      // 한투에서 accesstoken 얻어오는 연결
      KisTokenCreateRes kisTokenCreateRes = kisWebClient
              .post()
              .uri("/oauth2/tokenP")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(kisTokenCreateReq)
              .retrieve()
              .bodyToMono(KisTokenCreateRes.class)
              .block();

      accessToken = kisTokenCreateRes.getAccessToken();
      log.info("kis 접근 토큰 발급 완료");
    } catch (Exception e) {
      // 요청값이 제대로 가지 않아 한투에서 거부하는 경우
      throw new KisTokenRequestException();
    }

  }

  public String getAccessToken() {
    return accessToken;
  }
}
