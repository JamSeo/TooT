package com.realfinal.toot.api.stock.service;

import com.realfinal.toot.api.stock.mapper.StockMapper;
import com.realfinal.toot.api.stock.request.StockReq;
import com.realfinal.toot.api.stock.response.*;
import com.realfinal.toot.common.exception.stock.StockNotFoundException;
import com.realfinal.toot.common.exception.user.MySQLSearchException;
import com.realfinal.toot.common.util.JwtProviderUtil;
import com.realfinal.toot.common.util.PriceUtil;
import com.realfinal.toot.db.entity.Execution;
import com.realfinal.toot.db.entity.Interest;
import com.realfinal.toot.db.entity.Stock;
import com.realfinal.toot.db.entity.User;
import com.realfinal.toot.db.entity.UserStock;
import com.realfinal.toot.db.repository.ExecutionRepository;
import com.realfinal.toot.db.repository.InterestRepository;
import com.realfinal.toot.db.repository.StockRepository;
import com.realfinal.toot.db.repository.UserRepository;
import com.realfinal.toot.db.repository.UserStockRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class StockServiceImpl implements StockService {

    private final JwtProviderUtil jwtProviderUtil;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;
    private final InterestRepository interestRepository;
    private final UserStockRepository userStockRepository;
    private final ExecutionRepository executionRepository;
    private final PriceUtil priceUtil;


    /**
     * 주식 매수
     *
     * @param accessToken
     * @param stockReq    주식 종목번호 + 매수할 주식 수
     * @return 매수에 성공한 주식 수(잔고가 부족할 경우 매수를 신청한 주식 수보다 적게 구매)
     */
    @Transactional
    public Integer buyStock(String accessToken, StockReq stockReq) {
        log.info("StockServiceImpl_buyStock_start: " + stockReq);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        String stockId = stockReq.getStockId();
        Integer count = stockReq.getCount();

        if (count <= 0) {
            log.info("StockServiceImpl_buyStock_end: count is less than 1 -> return 0");
            return 0;
        }

        Integer price = priceUtil.getCurrentPrice(stockId);
        Long totalPrice = Long.valueOf((long) price * count);
        Long cash = user.getCash();

        if (cash < totalPrice) {
            count = (int) (cash / price);
            if (count == 0) {
                log.info(
                    "StockServiceImpl_buyStock_end: change 0");
                return 0;
            }
            log.info("StockServiceImpl_buyStock_mid: change " + count);
            totalPrice = Long.valueOf((long) price * count);
        }
        Stock stock = stockRepository.findById(stockId).orElseThrow(StockNotFoundException::new);

        UserStock userStock = userStockRepository.findByUserAndStockAndBankruptcyNo(user, stock,
            user.getBankruptcyNo());

        String userName = user.getName();
        Integer bankruptcyNo = user.getBankruptcyNo();
        String stockName = stock.getStockName();

        if (userStock == null) {
            userStock = StockMapper.INSTANCE.toUserStock(user, stock, count, price);
            userStockRepository.save(userStock);
        } else {
            Integer averagePrice = userStock.getAveragePrice();
            Integer hold = userStock.getHold();
            Long priceSum = Long.valueOf(
                (long) averagePrice * hold + totalPrice);
            hold += count;
            userStock.updateHold(hold);
            averagePrice = (int) (priceSum / hold);
            userStock.updateAveragePrice(averagePrice);
        }
        cash -= totalPrice;
        user.updateCash(cash);
        Execution execution = StockMapper.INSTANCE.toExecution(stock, user, price, count, true);

        executionRepository.save(execution);

        log.info("StockServiceImpl_buyStock_end: " + count);
        return count;
    }

    /**
     * 전체 주식의 주가 정보를 제공
     *
     * @param accessToken
     * @return [ 종목 번호, 종목명, 현재가, 전일 대비 등락가격, 전일 대비 등락률, 관심 종목으로 등록 여부 ] 리스트
     */
    public List<AllStockRes> showAll(String accessToken) {
//        log.info("StockServiceImpl_showAll_start");
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        List<Stock> stockList = stockRepository.findAll();
        List<AllStockRes> allStockResList = new ArrayList<>();

        for (Stock stock : stockList) {
            String stockId = stock.getId();
            Interest interest = interestRepository.findByUserAndStock(user, stock);
            Boolean liked = interest != null && interest.getIsInterested();
            Integer currentPrice = priceUtil.getCurrentPrice(stockId);
            String priceDifference = priceUtil.getPriceDifference(stockId);
            String rateDifference = priceUtil.getRateDifference(stockId);
            AllStockRes allStockRes = StockMapper.INSTANCE.toAllStockRes(stock, currentPrice,
                priceDifference, rateDifference, liked);
            allStockResList.add(allStockRes);
        }

//        log.info("StockServiceImpl_showAll_end: " + !allStockResList.isEmpty());
        return allStockResList;
    }

    /**
     * 사용자의 평가액 계산
     *
     * @param accessToken
     * @return 시드머니, 계좌 잔고, 주식을 반영한 총 평가액
     */
    public UserValueRes calculateValue(String accessToken) {
        log.info("StockServiceImpl_calculateValue_start");
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);

        UserValueRes userValueRes = priceUtil.calculateUserValue(userId);
        log.info("StockServiceImpl_calculateValue_end: " + userValueRes);
        return userValueRes;
    }

    /**
     * 거래량 상위 10개 종목 조회
     *
     * @return [ 등수, 종목번호, 종목명, 현재가, 전일 대비 등락률 ] 리스트(10개)
     */
    public List<StockRankRes> rankByVolume() {
//        log.info("StockServiceImpl_rankByVolume_start");
        List<StockRankRes> stockRankResList = priceUtil.getRankByVolume();
//        log.info("StockServiceImpl_rankByVolume_end");
        return stockRankResList;
    }

    /**
     * 사용자가 등록한 관심 종목 목록 조회
     *
     * @param accessToken
     * @return [ 종목번호, 종목명, 현재가, 전일 대비 등락가격, 전일 대비 등락률, 관심 종목 등록 여부(무조건 true) ] 리스트
     */
    public List<InterestRes> showInterest(String accessToken) {
        log.info("StockServiceImpl_showInterest_start");
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        List<Interest> interestList = interestRepository.findAllByUser(user);
        if (interestList.isEmpty()) {
            String userName = user.getName();
            log.info("StockServiceImpl_showInterest_end: null");
            return null;
        }
        List<InterestRes> interestResList = new ArrayList<>();

        for (Interest interest : interestList) {
            if (interest.getIsInterested()) {
                Stock stock = interest.getStock();
                String stockId = stock.getId();
                Integer currentPrice = priceUtil.getCurrentPrice(stockId);
                Integer priceDifference = Integer.parseInt(priceUtil.getPriceDifference(stockId));
                Double rateDifference = Double.parseDouble(priceUtil.getRateDifference(stockId));
                InterestRes interestRes = StockMapper.INSTANCE.toInterestRes(stock, currentPrice,
                    priceDifference, rateDifference, true);
                interestResList.add(interestRes);
            }
        }

        log.info("StockServiceImpl_showInterest_end");
        return interestResList;
    }

    /**
     * 상세 주식 조회
     *
     * @param stockId
     * @param accessToken
     * @return 종목명, 분봉 리스트, 일봉 리스트, 주봉 리스트, 시가총액, 현재가, 총 주식 수, 산업군, 세부 산업군, 52주 최저가, 52주 최고가, 상세설명,
     * 관심 종목 등록 여부, 사용자 보유 주식 수
     */
    public SpecificStockRes getStockInfo(String stockId, String accessToken) {
        log.info("StockServiceImpl_getStockInfo_start: " + stockId);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Stock stock = stockRepository.findById(stockId).orElseThrow(MySQLSearchException::new);
        UserStock userStock = userStockRepository.findByUserAndStockAndBankruptcyNo(user, stock,
            user.getBankruptcyNo());
        Interest interest = interestRepository.findByUserAndStock(user, stock);
        Boolean liked = interest != null && interest.getIsInterested();
        Integer hold = userStock == null ? 0 : userStock.getHold();

        List<MinuteRes> minCandle = priceUtil.getMinCandle(stockId);
        List<DayWeekRes> dayCandle = priceUtil.getDayCandle(stockId);
        List<DayWeekRes> weekCandle = priceUtil.getWeekCandle(stockId);
        Integer currentPrice = priceUtil.getCurrentPrice(stockId);
        Integer min52 = priceUtil.getMin52(stockId);
        Integer max52 = priceUtil.getMax52(stockId);
        String totalPrice = priceUtil.getTotalPrice(stockId);
        String per = priceUtil.getPer(stockId);
        String pbr = priceUtil.getPbr(stockId);
        Integer priceDifference = Integer.parseInt(priceUtil.getPriceDifference(stockId));
        Double rateDifference = Double.parseDouble(priceUtil.getRateDifference(stockId));
        SpecificStockRes specificStockRes = StockMapper.INSTANCE.toSpecificStockRes(stock,
            minCandle, dayCandle, weekCandle, totalPrice, currentPrice, min52, max52, per, pbr,
            priceDifference, rateDifference, liked, hold);
        log.info("StockServiceImpl_getStockInfo_end: " + specificStockRes);
        return specificStockRes;
    }

    /**
     * 사용자 보유 종목 조회
     *
     * @param accessToken
     * @return [ 종목번호, 종목명, 보유 주식 수, 평균단가, 현재가, 총 평가금액(보유 주식 수 * 현재가), 수익, 수익률 ] 리스트
     */
    public List<MyStockRes> myStocks(String accessToken) {
        log.info("StockServiceImpl_myStocks_start");
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        List<UserStock> userStockList = userStockRepository.findAllByUserAndBankruptcyNo(user,
            user.getBankruptcyNo());

        String userName = user.getName();
        Integer bankruptcyNo = user.getBankruptcyNo();

        List<MyStockRes> myStockResList = new ArrayList<MyStockRes>();

        if (userStockList.isEmpty()) {
            log.info("StockServiceImpl_myStocks_end: empty List");
            return myStockResList;
        }

        for (UserStock userStock : userStockList) {
            if (userStock.getHold() > 0) {
                //주식 정보
                Stock stock = userStock.getStock();
                String stockId = stock.getId();
                Integer currentPrice = priceUtil.getCurrentPrice(stockId);

                //사용자 보유 주식 정보
                Integer averagePrice = userStock.getAveragePrice();
                Integer hold = userStock.getHold();
                Long totalPrice = Long.valueOf((long) currentPrice * hold);
                Long profit = totalPrice - Long.valueOf((long) averagePrice * hold);
                Double profitRate = Double.valueOf(
                    100.0 * (currentPrice - averagePrice) / averagePrice);
                profitRate = Math.round(profitRate * 100.0) / 100.0;

                //사용자가 보유 중인 주식 정보 반환
                MyStockRes myStockRes = StockMapper.INSTANCE.toMyStockRes(stock, hold, averagePrice,
                    currentPrice, totalPrice, profit, profitRate);
                myStockResList.add(myStockRes);
            }
        }

        log.info("StockServiceImpl_myStocks_end");
        return myStockResList;
    }

    /**
     * 사용자 거래 내역
     *
     * @param accessToken
     * @return [ 거래일시, 매수여부, 종목번호, 종목명, 거래 주식 수, 거래가격, 총 거래가격 ] 리스트
     */
    public List<ExecutionRes> myAllExecution(String accessToken) {
        log.info("StockServiceImpl_myAllExecution_start");
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Integer bankruptcyNo = user.getBankruptcyNo();
        List<Execution> executionList = executionRepository.findAllByUserAndBankruptcyNo(user,
            bankruptcyNo);

        if (executionList.isEmpty()) {
            log.info("StockServiceImpl_myAllExecution_end: null");
            return null;
        }

        List<ExecutionRes> executionResList = new ArrayList<>();
        for (Execution execution : executionList) {
            executionResList.add(StockMapper.INSTANCE.toExecutionRes(execution,
                Long.valueOf((long) execution.getPrice() * execution.getAmount())));
        }

        if (!executionResList.isEmpty()) {
            executionResList.sort(Comparator.comparing(ExecutionRes::getDealAt).reversed());
        }

        log.info("StockServiceImpl_myAllExecution_end");
        return executionResList;
    }

    /**
     * 사용자 보유 종목 회사별 조회
     *
     * @param accessToken
     * @param stockId
     * @return { 종목번호, 종목명, 보유 주식 수, 평균단가, 현재가, 총 평가금액(보유 주식 수 * 현재가), 수익, 수익률 }
     */
    public MyStockRes myStock(String accessToken, String stockId) {
        log.info("StockServiceImpl_myStock_start: " + stockId);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Stock stock = stockRepository.findById(stockId).orElseThrow(MySQLSearchException::new);
        UserStock userStock = userStockRepository.findByUserAndStockAndBankruptcyNo(user, stock,
            user.getBankruptcyNo());

        String userName = user.getName();
        Integer bankruptcyNo = user.getBankruptcyNo();

        if (userStock == null) {
            log.info(
                "StockServiceImpl_myStock_end: null");
            return null;
        }

        MyStockRes myStockRes = null;

        if (userStock.getHold() >= 0) {
            //주식 정보
            Integer currentPrice = priceUtil.getCurrentPrice(stockId);

            //사용자 보유 주식 정보
            Integer averagePrice = userStock.getAveragePrice();
            Integer hold = userStock.getHold();
            Long totalPrice = Long.valueOf((long) currentPrice * hold);
            Long profit = totalPrice - Long.valueOf((long) averagePrice * hold);
            Double profitRate = Double.valueOf(
                100.0 * (currentPrice - averagePrice) / averagePrice);
            profitRate = Math.round(profitRate * 100.0) / 100.0;

            //사용자가 보유 중인 주식 정보 반환
            myStockRes = StockMapper.INSTANCE.toMyStockRes(stock, hold, averagePrice,
                currentPrice, totalPrice, profit, profitRate);
        }

        log.info("StockServiceImpl_myStock_end: " + myStockRes);
        return myStockRes;
    }

    /**
     * 사용자 특정 주식 거래 내역
     *
     * @param stockId
     * @param accessToken
     * @return [ 거래일시, 매수여부, 종목번호, 종목명, 거래 주식 수, 거래가격, 총 거래가격 ] 리스트
     */
    public List<ExecutionRes> myExecution(String stockId, String accessToken) {
        log.info("StockServiceImpl_myExecution_start: " + stockId);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Stock stock = stockRepository.findById(stockId).orElseThrow(MySQLSearchException::new);
        List<Execution> executionList = executionRepository.findAllByUserAndBankruptcyNoAndStock(
            user,
            user.getBankruptcyNo(), stock);

        if (executionList.isEmpty()) {
            log.info("StockServiceImpl_myExecution_end: null");
            return null;
        }

        List<ExecutionRes> executionResList = new ArrayList<>();
        for (Execution execution : executionList) {
            executionResList.add(StockMapper.INSTANCE.toExecutionRes(execution,
                Long.valueOf((long) execution.getPrice() * execution.getAmount())));
        }

        if (!executionResList.isEmpty()) {
            executionResList.sort(Comparator.comparing(ExecutionRes::getDealAt).reversed());
        }

        log.info("StockServiceImpl_myExecution_end");
        return executionResList;
    }

    /**
     * 주식 매도
     *
     * @param accessToken
     * @param stockReq    종목번호, 매도할 주식 수
     * @return 매도 성공한 주식 수(보유 주식보다 많은 주식을 판매하려는 경우 보유한 주식 수만 반환)
     */
    @Transactional
    public Integer sellStock(String accessToken, StockReq stockReq) {
        log.info("StockServiceImpl_sellStock_start: " + stockReq);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        String stockId = stockReq.getStockId();
        Stock stock = stockRepository.findById(stockId).orElseThrow(MySQLSearchException::new);
        Integer bankruptcyNo = user.getBankruptcyNo();
        UserStock userStock = userStockRepository.findByUserAndStockAndBankruptcyNo(user, stock,
            bankruptcyNo);
        Integer count = stockReq.getCount();

        if (count <= 0) {
            log.info("StockServiceImpl_sellStock_end: 0");
            return 0;
        }

        String userName = user.getName();
        String stockName = stock.getStockName();
        if (userStock == null) {
            log.info("StockServiceImpl_sellStock_end: 0");
            return 0;
        } else if (userStock.getHold() < count) {
            Integer hold = userStock.getHold();
            count = hold;
        }

        Integer price = priceUtil.getCurrentPrice(stockId);
        Long totalPrice = Long.valueOf((long) priceUtil.getCurrentPrice(stockId) * count);
        totalPrice *= 99685;
        totalPrice /= 100000;
        Integer hold = userStock.getHold() - count;
        userStock.updateHold(hold);
        Long cash = user.getCash() + totalPrice;
        user.updateCash(cash);
        Execution execution = StockMapper.INSTANCE.toExecution(stock, user, price, count, false);

        executionRepository.save(execution);
        log.info("StockServiceImpl_sellStock_end: " + count);
        return count;
    }

    /**
     * @param stockId
     * @param accessToken
     * @return true(관심 종목 추가 성공), false(관심 종목 추가 실패)
     */
    @Transactional
    public Boolean addInterest(String stockId, String accessToken) {
        log.info("StockServiceImpl_addInterest_start: " + stockId);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Stock stock = stockRepository.findById(stockId).orElseThrow(MySQLSearchException::new);
        Interest interest = interestRepository.findByUserAndStock(user, stock);

        if (interest == null) {
            String stockName = stock.getStockName();
            String userName = user.getName();
            interest = StockMapper.INSTANCE.toInterest(user, stock, true);
            interestRepository.save(interest);
        } else {
            interest.setInterest(true);
        }

        log.info("StockServiceImpl_addInterest_end: true");
        return true;
    }

    /**
     * @param stockId
     * @param accessToken
     * @return true(관심 종목 취소 성공), false(관심 종목 취소 실패)
     */
    @Transactional
    public Boolean cancelInterest(String stockId, String accessToken) {
        log.info("StockServiceImpl_cancelInterest_start: " + stockId);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Stock stock = stockRepository.findById(stockId).orElseThrow(MySQLSearchException::new);
        Interest interest = interestRepository.findByUserAndStock(user, stock);

        if (interest == null) {
            String stockName = stock.getStockName();
            String userName = user.getName();
            log.info("StockServiceImpl_cancelInterest_end: MySQLSearchException");
            throw new MySQLSearchException();
        } else {
            interest.setInterest(false);
        }

        log.info("StockServiceImpl_cancelInterest_end: true");
        return true;
    }
}
