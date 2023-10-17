package com.realfinal.toot.api.stock.controller;

import com.realfinal.toot.api.stock.request.StockReq;
import com.realfinal.toot.api.stock.response.AllStockRes;
import com.realfinal.toot.api.stock.response.ExecutionRes;
import com.realfinal.toot.api.stock.response.InterestRes;
import com.realfinal.toot.api.stock.response.MyStockRes;
import com.realfinal.toot.api.stock.response.SpecificStockRes;
import com.realfinal.toot.api.stock.response.StockRankRes;
import com.realfinal.toot.api.stock.response.UserValueRes;
import com.realfinal.toot.api.stock.service.StockService;
import com.realfinal.toot.common.model.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/stock")
@RestController
public class StockController {

    private final StockService stockService;

    /**
     * 전체 주식의 주가 정보를 제공
     *
     * @param accessToken
     * @return [ 종목 번호, 종목명, 현재가, 전일 대비 등락가격, 전일 대비 등락률, 관심 종목으로 등록 여부 ] 리스트
     */
    @GetMapping("/showall")
    public CommonResponse<List<AllStockRes>> showAll(
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_showAll_start");
        List<AllStockRes> allStockResList = stockService.showAll(accessToken);
        log.info("StockController_showAll_end");
        return CommonResponse.success(allStockResList);
    }

    /**
     * 주식 매수
     *
     * @param accessToken
     * @param stockReq    주식 종목번호 + 매수할 주식 수
     * @return 매수에 성공한 주식 수(잔고가 부족할 경우 매수를 신청한 주식 수보다 적게 구매)
     */
    @PostMapping("/buy")
    public CommonResponse<Integer> buyStock(
        @RequestHeader(value = "accesstoken", required = false) String accessToken,
        @RequestBody StockReq stockReq) {
        log.info("StockController_buyStock_start: " + stockReq);
        Integer boughtStock = stockService.buyStock(accessToken, stockReq);
        log.info("StockController_buyStock_end: return " + boughtStock);
        return CommonResponse.success(boughtStock);
    }

    /**
     * 사용자의 평가액 계산
     *
     * @param accessToken
     * @return 시드머니, 계좌 잔고, 주식을 반영한 총 평가액
     */
    @GetMapping("/value")
    public CommonResponse<UserValueRes> calculateValue(
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_calculateValue_start");
        UserValueRes userValueRes = stockService.calculateValue(accessToken);
        log.info("StockController_calculateValue_end: " + userValueRes);
        return CommonResponse.success(userValueRes);
    }

    /**
     * 거래량 상위 10개 종목 조회
     *
     * @return [ 등수, 종목번호, 종목명, 현재가, 전일 대비 등락률 ] 리스트(10개)
     */
    @GetMapping("/rank")
    public CommonResponse<List<StockRankRes>> rankByVolume() {
        log.info("StockController_rankByVolume_start");
        List<StockRankRes> stockRankResList = stockService.rankByVolume();
        log.info("StockController_rankByVolume_end");
        return CommonResponse.success(stockRankResList);
    }

    /**
     * 사용자가 등록한 관심 종목 목록 조회
     *
     * @param accessToken
     * @return [ 종목번호, 종목명, 현재가, 전일 대비 등락가격, 전일 대비 등락률, 관심 종목 등록 여부(무조건 true) ] 리스트
     */
    @GetMapping("/interest/show")
    public CommonResponse<List<InterestRes>> showInterest(
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_showInterest_start");
        List<InterestRes> interestResList = stockService.showInterest(accessToken);
        log.info("StockController_showInterest_end");
        return CommonResponse.success(interestResList);
    }

    /**
     * 상세 주식 조회
     *
     * @param stockId
     * @param accessToken
     * @return 종목명, 분봉 리스트, 일봉 리스트, 주봉 리스트, 시가총액, 현재가, 총 주식 수, 산업군, 세부 산업군, 52주 최저가, 52주 최고가, 상세설명,
     * 관심 종목 등록 여부, 사용자 보유 주식 수
     */
    @GetMapping("/{stockId}")
    public CommonResponse<SpecificStockRes> getStockInfo(@PathVariable("stockId") String stockId,
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_getStockInfo_start: stockId = " + stockId);
        SpecificStockRes specificStockRes = stockService.getStockInfo(stockId, accessToken);
        log.info("StockController_getStockInfo_end: " + specificStockRes);
        return CommonResponse.success(specificStockRes);
    }

    /**
     * 사용자 보유 종목 목록 조회
     *
     * @param accessToken
     * @return [ 종목번호, 종목명, 보유 주식 수, 평균단가, 현재가, 총 평가금액(보유 주식 수 * 현재가), 수익, 수익률 ] 리스트
     */
    @GetMapping("/my")
    public CommonResponse<List<MyStockRes>> myStocks(
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_myStocks_start");
        List<MyStockRes> myStockResList = stockService.myStocks(accessToken);
        log.info("StockController_myStocks_end");
        return CommonResponse.success(myStockResList);
    }

    /**
     * 사용자 거래 내역
     *
     * @param accessToken
     * @return [ 거래일시, 매수여부, 종목번호, 종목명, 거래 주식 수, 거래가격, 총 거래가격 ] 리스트
     */
    @GetMapping("/execution")
    public CommonResponse<List<ExecutionRes>> myAllExecution(
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_myAllExecution_start");
        List<ExecutionRes> executionResList = stockService.myAllExecution(accessToken);
        log.info("StockController_myAllExecution_end");
        return CommonResponse.success(executionResList);
    }

    /**
     * 사용자 보유 종목 회사별 조회
     *
     * @param accessToken, stockId
     * @return { 종목번호, 종목명, 보유 주식 수, 평균단가, 현재가, 총 평가금액(보유 주식 수 * 현재가), 수익, 수익률 }
     */
    @GetMapping("/my/{stockId}")
    public CommonResponse<MyStockRes> myStock(
        @RequestHeader(value = "accesstoken", required = false) String accessToken,
        @PathVariable("stockId") String stockId) {
        log.info("StockController_myStock_start");
        MyStockRes myStockRes = stockService.myStock(accessToken, stockId);
        log.info("StockController_myStock_end");
        return CommonResponse.success(myStockRes);
    }

    /**
     * 사용자 특정 주식 거래 내역
     *
     * @param stockId
     * @param accessToken
     * @return [ 거래일시, 매수여부, 종목번호, 종목명, 거래 주식 수, 거래가격, 총 거래가격 ] 리스트
     */
    @GetMapping("/execution/{stockId}")
    public CommonResponse<List<ExecutionRes>> myExecution(@PathVariable("stockId") String stockId,
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_myExecution_start: " + stockId);
        List<ExecutionRes> executionResList = stockService.myExecution(stockId, accessToken);
        log.info("StockController_myExecution_end");
        return CommonResponse.success(executionResList);
    }

    /**
     * 주식 매도
     *
     * @param accessToken
     * @param stockReq    종목번호, 매도할 주식 수
     * @return 매도 성공한 주식 수(보유 주식보다 많은 주식을 판매하려는 경우 보유한 주식 수만 반환)
     */
    @PostMapping("/sell")
    public CommonResponse<Integer> sellStock(
        @RequestHeader(value = "accesstoken", required = false) String accessToken,
        @RequestBody StockReq stockReq) {
        log.info("StockController_sellStock_start: " + stockReq);
        Integer soldStock = stockService.sellStock(accessToken, stockReq);
        log.info("StockController_sellStock_end: " + soldStock);
        return CommonResponse.success(soldStock);
    }

    /**
     * @param stockId
     * @param accessToken
     * @return true(관심 종목 추가 성공), false(관심 종목 추가 실패)
     */
    @PatchMapping("/interest/add/{stockId}")
    public CommonResponse<Boolean> addInterest(@PathVariable("stockId") String stockId,
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_addInterest_start: " + stockId);
        Boolean isAdded = stockService.addInterest(stockId, accessToken);
        log.info("StockController_addInterest_end: " + isAdded);
        return CommonResponse.success(isAdded);
    }

    /**
     * @param stockId
     * @param accessToken
     * @return true(관심 종목 취소 성공), false(관심 종목 취소 실패)
     */
    @PatchMapping("/interest/cancel/{stockId}")
    public CommonResponse<Boolean> cancelInterest(@PathVariable("stockId") String stockId,
        @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("StockController_cancelInterest_start: " + stockId);
        Boolean isCanceled = stockService.cancelInterest(stockId, accessToken);
        log.info("StockController_cancelInterest_end: " + isCanceled);
        return CommonResponse.success(isCanceled);
    }
}
