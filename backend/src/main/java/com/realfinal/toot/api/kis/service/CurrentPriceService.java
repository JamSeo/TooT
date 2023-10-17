package com.realfinal.toot.api.kis.service;

import com.realfinal.toot.api.kis.response.CurrentPriceRes;
import com.realfinal.toot.api.kis.util.KisAccessTokenUtil;
import com.realfinal.toot.api.kis.util.OpenCronUtil;
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
public class CurrentPriceService {

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
                Thread.sleep(30000);
                openCronUtil.startTasks();
                fetchCurrentPriceForBatch1();
                Thread.sleep(1000);
                fetchCurrentPriceForBatch2();
                Thread.sleep(1000);
                fetchCurrentPriceForBatch3();
                Thread.sleep(1000);
                fetchCurrentPriceForBatch4();
                Thread.sleep(1000);
                openCronUtil.stopTasks();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(fixedRate = 5000, initialDelay = 31250)
    public void fetchCurrentPriceForBatch1() {
        getCurrentPrice(kospi32Config.company1, "현재가 기업1");
    }

    @Scheduled(fixedRate = 5000, initialDelay = 32500)
    public void fetchCurrentPriceForBatch2() {
        getCurrentPrice(kospi32Config.company2, "현재가 기업2");
    }

    @Scheduled(fixedRate = 5000, initialDelay = 33750)
    public void fetchCurrentPriceForBatch3() {
        getCurrentPrice(kospi32Config.company3, "현재가 기업3");
    }

    @Scheduled(fixedRate = 5000, initialDelay = 35000)
    public void fetchCurrentPriceForBatch4() {
        getCurrentPrice(kospi32Config.company4, "현재가 기업4");
    }

    private void getCurrentPrice(List<String> companies, String companyInfo) {
//        log.info("CurrentPriceService_getCurrentPrice_start: " + companyInfo + " " + companies.toString());
        if (!openCronUtil.shouldRun()) {
            if (companyInfo.equals("현재가 기업4")) {
                priceUtil.updateRankList(priceUtil.getNextRankState());
            }
            return;
        }
        long start = System.currentTimeMillis();
        CurrentPriceRes[] resList = new CurrentPriceRes[companies.size()];
        for (int i = 0; i < companies.size(); i++) {
            CurrentPriceRes result = fetchCurrentPriceForCompany(companies.get(i));
            resList[i] = result;
        }
        priceUtil.updatePrice(resList); //8개 단위로 업데이트
        if (companyInfo.equals("현재가 기업4")) { // 32개 모두 갱신할 때 state 바꿔주기
            priceUtil.updateState();
        }
//        log.info("CurrentPriceService_getCurrentPrice_end: 8개씩 호출");
    }

    private CurrentPriceRes fetchCurrentPriceForCompany(String companyId) {
        try {
            CurrentPriceRes currentPriceRes = kisWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/uapi/domestic-stock/v1/quotations/inquire-price")
                    .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                    .queryParam("FID_INPUT_ISCD", companyId)
                    .build())
                .headers(httpHeaders -> {
                    httpHeaders.add("authorization",
                        "Bearer " + kisAccessTokenUtil.getAccessToken());
                    httpHeaders.add("appkey", kisConfig.getAppKey());
                    httpHeaders.add("appsecret", kisConfig.getAppSecret());
                    httpHeaders.add("tr_id", "FHKST01010100");
                })
                .retrieve()
                .bodyToMono(CurrentPriceRes.class)
                .block();
            currentPriceRes.setCorp(companyId);
            return currentPriceRes;
        } catch (HttpServerErrorException.InternalServerError e) {
            log.info("CurrentPriceService_fetchCurrentPriceForCompany_mid: 호출 횟수 초과");
            throw new KisApiCallTooManyException();
        } catch (HttpClientErrorException.NotFound e) {
            log.info("CurrentPriceService_fetchCurrentPriceForCompany_mid: 접근 거부");
            throw new KisApiCallDeniedException();
        }
    }
}
