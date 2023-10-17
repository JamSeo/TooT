package com.realfinal.toot.api.kis.service;

import com.realfinal.toot.api.kis.response.CurrentPriceRes;
import com.realfinal.toot.api.kis.response.PeriodPriceRes;
import com.realfinal.toot.api.kis.util.KisAccessTokenUtil;
import com.realfinal.toot.api.kis.util.TimeToStringUtil;
import com.realfinal.toot.common.exception.kis.KisApiCallDeniedException;
import com.realfinal.toot.common.exception.kis.KisApiCallTooManyException;
import com.realfinal.toot.common.util.PriceUtil;
import com.realfinal.toot.config.KisConfig;
import com.realfinal.toot.config.Kospi32Config;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableAsync
public class PeriodPriceService {

    private final KisConfig kisConfig;
    private final Kospi32Config kospi32Config;
    private final KisAccessTokenUtil kisAccessTokenUtil;
    private final PriceUtil priceUtil;
    private final String KIS_URI = "https://openapi.koreainvestment.com:9443";
    private final WebClient kisWebClient = WebClient.builder().baseUrl(KIS_URI).build();

    @PostConstruct
    public void init() {
        try {
            Thread.sleep(3000);
            fetchPeriodPriceForBatch1_D();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch1_W();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch2_D();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch2_W();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch3_D();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch3_W();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch4_D();
            Thread.sleep(1000);
            fetchPeriodPriceForBatch4_W();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch1_D() {
        getPeriodPrice(kospi32Config.company1, "D");
    }

    @Scheduled(cron = "3 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch2_D() {
        getPeriodPrice(kospi32Config.company2, "D");
    }

    @Scheduled(cron = "6 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch3_D() {
        getPeriodPrice(kospi32Config.company3, "D");
    }

    @Scheduled(cron = "9 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch4_D() {
        getPeriodPrice(kospi32Config.company4, "D");
    }

    @Scheduled(cron = "12 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch1_W() {
        getPeriodPrice(kospi32Config.company1, "W");
    }

    @Scheduled(cron = "15 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch2_W() {
        getPeriodPrice(kospi32Config.company2, "W");
    }

    @Scheduled(cron = "18 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch3_W() {
        getPeriodPrice(kospi32Config.company3, "W");
    }

    @Scheduled(cron = "21 0 7 ? * MON-FRI")
    public void fetchPeriodPriceForBatch4_W() {
        getPeriodPrice(kospi32Config.company4, "W");
    }

    private void getPeriodPrice(List<String> companies, String div) {
        for (String company : companies) {
            PeriodPriceRes periodPriceRes = fetchPeriodPriceForCompany(company, div);
            if (div == "D") {
                priceUtil.updateDayCandle(periodPriceRes);
            } else {
                priceUtil.updateWeekCandle(periodPriceRes);
            }
        }
    }

    private PeriodPriceRes fetchPeriodPriceForCompany(String companyId, String div) {
        try {
            PeriodPriceRes periodPriceRes = kisWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/uapi/domestic-stock/v1/quotations/inquire-daily-itemchartprice")
                    .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                    .queryParam("FID_INPUT_ISCD", companyId)
                    .queryParam("FID_INPUT_DATE_1", TimeToStringUtil.getYearBeforeAsString())
                    .queryParam("FID_INPUT_DATE_2", TimeToStringUtil.getTodayAsString())
                    .queryParam("FID_PERIOD_DIV_CODE", div)
                    .queryParam("FID_ORG_ADJ_PRC", "0")
                    .build())
                .headers(httpHeaders -> {
                    httpHeaders.add("content-type", "application/json; charset=utf-8");
                    httpHeaders.add("authorization",
                        "Bearer " + kisAccessTokenUtil.getAccessToken());
                    httpHeaders.add("appkey", kisConfig.getAppKey());
                    httpHeaders.add("appsecret", kisConfig.getAppSecret());
                    httpHeaders.add("tr_id", "FHKST03010100");
                })
                .retrieve()
                .bodyToMono(PeriodPriceRes.class)
                .block();
            periodPriceRes.setCorp(companyId);
            return periodPriceRes;
        } catch (HttpServerErrorException.InternalServerError e) {
            log.info("CurrentPriceService_fetchCurrentPriceForCompany_mid: 호출 횟수 초과");
            throw new KisApiCallTooManyException();
        } catch (HttpClientErrorException.NotFound e) {
            log.info("CurrentPriceService_fetchCurrentPriceForCompany_mid: 접근 거부");
            throw new KisApiCallDeniedException();
        }
    }

}
