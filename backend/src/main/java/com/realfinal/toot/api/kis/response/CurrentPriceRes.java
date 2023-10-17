package com.realfinal.toot.api.kis.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CurrentPriceRes {

  private Output output;
  private String corp; // 종목번호

  public void setCorp(String corp) {
    this.corp = corp;
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Output {
    private String stck_prpr; // 현재가
    private String prdy_vrss; // 전일 대비 금액 변동
    private String prdy_ctrt; // 전일 대비율
    private String acml_vol; // 누적 거래량
    private String hts_avls; // 시가총액
    private String per; // PER
    private String pbr; // PBR
    private String w52_hgpr; // 52주 최고가
    private String w52_lwpr; // 52주 최저가
  }
}