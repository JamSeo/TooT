package com.realfinal.toot.api.kis.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MinutePriceRes {

  private Output1 output1;
  private List<Output2> output2 = null;
  private String corp; // 종목번호

  public void setCorp(String corp) {
    this.corp = corp;
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Output1 {
  }

  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Output2 {

    private String stck_cntg_hour; // 체결 시간(HHMMSS)
    private String stck_prpr; // 현재가
    private String cntg_vol; // 체결 거래량
  }

}
