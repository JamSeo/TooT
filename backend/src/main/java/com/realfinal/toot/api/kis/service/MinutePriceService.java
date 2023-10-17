package com.realfinal.toot.api.kis.service;

import com.realfinal.toot.api.kis.response.MinutePriceRes;
import com.realfinal.toot.api.kis.util.KisAccessTokenUtil;
import com.realfinal.toot.api.kis.util.OpenCronUtil;
import com.realfinal.toot.api.kis.util.TimeToStringUtil;
import com.realfinal.toot.common.exception.kis.KisApiCallDeniedException;
import com.realfinal.toot.common.exception.kis.KisApiCallTooManyException;
import com.realfinal.toot.common.util.PriceUtil;
import com.realfinal.toot.config.KisConfig;
import com.realfinal.toot.config.Kospi32Config;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MinutePriceService {

    private final KisConfig kisConfig;
    private final Kospi32Config kospi32Config;
    private final OpenCronUtil openCronUtil;
    private final KisAccessTokenUtil kisAccessTokenUtil;
    private final PriceUtil priceUtil;
    private final String KIS_URI = "https://openapi.koreainvestment.com:9443";
    private final WebClient kisWebClient = WebClient.builder().baseUrl(KIS_URI).build();

    @PostConstruct
    public void init() {
        if (!openCronUtil.shouldRun()) {
            try {
                Thread.sleep(40000);
                openCronUtil.startTasks();
                getMinutePrice();
                openCronUtil.stopTasks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 31000)
    public void getMinutePrice() {
        fetchMinutePrice(kospi32Config.totalCompany);
    }

    private void fetchMinutePrice(List<String> companies) {
        if (!openCronUtil.shouldRun()) {
            return;
        }
//        log.info("MinutePriceService_fetchMinutePrice_start:" + companies.toString());
        for (int i = 0; i < companies.size(); i++) {
            int finalI = i;
            try {
                MinutePriceRes result = kisWebClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                        .path("/uapi/domestic-stock/v1/quotations/inquire-time-itemchartprice")
                        .queryParam("FID_ETC_CLS_CODE", "")
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", companies.get(finalI))
                        .queryParam("FID_INPUT_HOUR_1", TimeToStringUtil.getCurrentTimeAsString())
                        .queryParam("FID_PW_DATA_INCU_YN", "N")
                        .build())
                    .headers(httpHeaders -> {
                        httpHeaders.add("content-type", "application/json; charset=utf-8");
                        httpHeaders.add("authorization",
                            "Bearer " + kisAccessTokenUtil.getAccessToken());
                        httpHeaders.add("appkey", kisConfig.getAppKey());
                        httpHeaders.add("appsecret", kisConfig.getAppSecret());
                        httpHeaders.add("tr_id", "FHKST03010200");
                        httpHeaders.add("custtype", "P");
                    })
                    .retrieve()
                    .bodyToMono(MinutePriceRes.class)
                    .block();
                result.setCorp(companies.get(finalI));
                priceUtil.updateMinCandle(result);

                // 마지막 원소가 아닐 때만 1초 대기
                if (i < companies.size() - 1) {
                    try {
                        Thread.sleep(1000);  // 1초 대기
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        log.info("MinutePriceService_fetchMinutePrice_mid: Sleep Interrupt");
                    }
                }
            } catch (HttpServerErrorException.InternalServerError e) {
                log.info("MinutePriceService_fetchMinutePrice_mid: 호출 횟수 초과");
                throw new KisApiCallTooManyException();
            } catch (HttpClientErrorException.NotFound e) {
                log.info("MinutePriceService_fetchMinutePrice_mid: 접근 거부");
                throw new KisApiCallDeniedException();
            }

        }
//        log.info("MinutePriceService_fetchMinutePrice_end: 1분 32개 호출");
    }

}
