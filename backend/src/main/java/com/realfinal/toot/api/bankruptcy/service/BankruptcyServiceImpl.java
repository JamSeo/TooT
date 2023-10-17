package com.realfinal.toot.api.bankruptcy.service;

import com.realfinal.toot.api.bankruptcy.mapper.BankruptcyMapper;
import com.realfinal.toot.api.bankruptcy.response.AllBankruptcyRes;
import com.realfinal.toot.api.bankruptcy.response.DetailBankruptcyRes;
import com.realfinal.toot.api.stock.mapper.StockMapper;
import com.realfinal.toot.api.stock.response.ExecutionRes;
import com.realfinal.toot.common.exception.bankruptcy.NoBankruptcyDataException;
import com.realfinal.toot.common.exception.user.MySQLSearchException;
import com.realfinal.toot.common.util.JwtProviderUtil;
import com.realfinal.toot.common.util.PriceUtil;
import com.realfinal.toot.db.entity.Bankruptcy;
import com.realfinal.toot.db.entity.Execution;
import com.realfinal.toot.db.entity.User;
import com.realfinal.toot.db.entity.UserStock;
import com.realfinal.toot.db.repository.BankruptcyRepository;
import com.realfinal.toot.db.repository.ExecutionRepository;
import com.realfinal.toot.db.repository.UserRepository;
import com.realfinal.toot.db.repository.UserStockRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BankruptcyServiceImpl implements BankruptcyService {

    private final JwtProviderUtil jwtProviderUtil;
    private final PriceUtil priceUtil;
    private final BankruptcyRepository bankruptcyRepository;
    private final UserRepository userRepository;
    private final UserStockRepository userStockRepository;
    private final ExecutionRepository executionRepository;
    private final Double RATIO = 70D;

    @Override
    public Boolean isBankruptcyAvailable(String accessToken) {
        log.info("BankruptcyServiceImpl_isBankruptcyAvailable_start: " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Long stockValue = calculateValue(user);
        Long totalAsset = stockValue + user.getCash();
        Long seedMoney = user.getSeedMoney();
        if (100L * totalAsset / seedMoney < RATIO) { // 시드머니 대비 총자산 비율이 30% 미만
            log.info("BankruptcyServiceImpl_isBankruptcyAvailable_end: true(파산 가능)");
            return true;
        } else {
            log.info("BankruptcyServiceImpl_isBankruptcyAvailable_end: false(파산 불가)");
            return false;
        }
    }

    @Override
    public void proceedBankrupt(String accessToken) {
        log.info("BankruptcyServiceImpl_goBankrupt_start: " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Integer bankruptcyNo = user.getBankruptcyNo();
        Long lastCash = user.getCash();
        Long lastSeedMoney = user.getSeedMoney();
        Long lastTotalAsset = calculateValue(user) + lastCash;
        Bankruptcy bankruptcy = new Bankruptcy(user, bankruptcyNo, lastCash, lastSeedMoney,
            lastTotalAsset, LocalDateTime.now());
        bankruptcyRepository.save(bankruptcy);
        // 파산 후 시드머니, 보유현금, 파산횟수 업데이트
        user.resetAfterBankruptcy();
        log.info("BankruptcyServiceImpl_goBankrupt_end: 파산 완료");
    }

    @Override
    public List<AllBankruptcyRes> getAllBankruptcy(String accessToken) {
        log.info("BankruptcyServiceImpl_getAllBankruptcy_start: " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        List<AllBankruptcyRes> allBankruptcyResList = new ArrayList<>();
        List<Bankruptcy> bankruptcyList = bankruptcyRepository.findAllByUser(user);
        if (bankruptcyList.isEmpty()) {
            throw new NoBankruptcyDataException();
        }
        for (Bankruptcy userBankruptcy : bankruptcyList) {
            AllBankruptcyRes allBankruptcyRes = BankruptcyMapper.INSTANCE.toAllBankruptcyRes(
                userBankruptcy);
            allBankruptcyResList.add(allBankruptcyRes);
        }
        log.info("BankruptcyServiceImpl_getAllBankruptcy_end: 파산 전체 기록 조회 완료");
        return allBankruptcyResList;
    }

    @Override
    public DetailBankruptcyRes getDetailBankruptcy(String accessToken, Integer bankruptcyNo) {
        log.info("BankruptcyServiceImpl_getDetailBankruptcy_start: " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        Bankruptcy userBankruptcy = bankruptcyRepository.findByUserAndBankruptcyNo(user,
            bankruptcyNo);
        if (userBankruptcy == null) {
            throw new NoBankruptcyDataException();
        }
        DetailBankruptcyRes detailBankruptcyRes = BankruptcyMapper.INSTANCE.toDetailBankruptcyRes(
            userBankruptcy);
        log.info("BankruptcyServiceImpl_getDetailBankruptcy_end: 파산 상세 기록 조회 완료");
        return detailBankruptcyRes;
    }

    @Override
    public List<ExecutionRes> getAllExecutionByBankruptcy(String accessToken,
        Integer bankruptcyNo) {
        log.info("BankruptcyServiceImpl_getAllExecutionByBankruptcy_start: " + accessToken + " " + bankruptcyNo);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
        List<Execution> bankruptcyExecutionList = executionRepository.findAllByUserAndBankruptcyNo(user,
            bankruptcyNo);

        if (bankruptcyExecutionList.isEmpty()) {
            log.info("BankruptcyServiceImpl_getAllExecutionByBankruptcy_mid: " + userId + "("
                + bankruptcyNo + ") has no history -> return null");
            throw new NoBankruptcyDataException();
        }

        List<ExecutionRes> bankruptcyExecutionResList = new ArrayList<>();
        for (Execution execution : bankruptcyExecutionList) {
            bankruptcyExecutionResList.add(StockMapper.INSTANCE.toExecutionRes(execution,
                Long.valueOf((long) execution.getPrice() * execution.getAmount())));
        }

        if (!bankruptcyExecutionResList.isEmpty()) {
            bankruptcyExecutionResList.sort(Comparator.comparing(ExecutionRes::getDealAt).reversed());
        }

        log.info("BankruptcyServiceImpl_getAllExecutionByBankruptcy_end: " + bankruptcyExecutionResList);
        return bankruptcyExecutionResList;
    }

    public Long calculateValue(User user) {
        log.info("BankruptcyServiceImpl_calculateValue_start: " + user);
        Long stockValue = 0L;
        List<UserStock> userStockList = userStockRepository.findAllByUserAndBankruptcyNo(user,
            user.getBankruptcyNo());
        if (!userStockList.isEmpty()) {
            for (UserStock userStock : userStockList) {
                stockValue += (long) priceUtil.getCurrentPrice(userStock.getStock().getId())
                    * userStock.getHold();
            }
        }
        log.info("BankruptcyServiceImpl_calculateValue_end: 평가액 산정 완료");
        return stockValue;
    }

}
