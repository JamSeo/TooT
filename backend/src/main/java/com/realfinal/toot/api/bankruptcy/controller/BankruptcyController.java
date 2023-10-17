package com.realfinal.toot.api.bankruptcy.controller;

import com.realfinal.toot.api.bankruptcy.response.AllBankruptcyRes;
import com.realfinal.toot.api.bankruptcy.response.DetailBankruptcyRes;
import com.realfinal.toot.api.bankruptcy.service.BankruptcyService;
import com.realfinal.toot.api.stock.response.ExecutionRes;
import com.realfinal.toot.common.model.CommonResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bankruptcy")
@RestController
public class BankruptcyController {

    private final BankruptcyService bankruptcyService;
    private final String SUCCESS = "success";

    @GetMapping("/filing")
    public CommonResponse<?> isBankruptcyAvailable(@RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("BankruptcyController_isBankruptcyAvailable_start");
        Boolean isAvailable = bankruptcyService.isBankruptcyAvailable(accessToken);
        log.info("BankruptcyController_isBankruptcyAvailable_end: 파산 가능 여부 " + isAvailable);
        return CommonResponse.success(isAvailable);
    }

    @PostMapping("/proceed")
    public CommonResponse<?> proceedBankrupt(@RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("BankruptcyController_proceedBankrupt_start");
        bankruptcyService.proceedBankrupt(accessToken);
        log.info("BankruptcyController_proceedBankrupt_end");
        return CommonResponse.success(SUCCESS);
    }

    @GetMapping("/all")
    public CommonResponse<List<AllBankruptcyRes>> getAllBankruptcy(@RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("BankruptcyController_getAllBankruptcy_start");
        List<AllBankruptcyRes> allBankruptcyResList = bankruptcyService.getAllBankruptcy(accessToken);
        log.info("BankruptcyController_getAllBankruptcy_end: 전체 파산 기록 " + allBankruptcyResList);
        return CommonResponse.success(allBankruptcyResList);
    }

    @GetMapping("/detail/{bankruptcyNo}")
    public CommonResponse<DetailBankruptcyRes> getDetailBankruptcy(@RequestHeader(value = "accesstoken", required = false) String accessToken, @PathVariable Integer bankruptcyNo) {
        log.info("BankruptcyController_getDetailBankruptcy_start");
        DetailBankruptcyRes detailBankruptcyRes = bankruptcyService.getDetailBankruptcy(accessToken, bankruptcyNo);
        log.info("BankruptcyController_getDetailBankruptcy_end: 상세 파산 기록 " + detailBankruptcyRes);
        return CommonResponse.success(detailBankruptcyRes);
    }

    @GetMapping("/detail/{bankruptcyNo}/execution")
    public CommonResponse<List<ExecutionRes>> getAllExecutionByBankruptcy(@RequestHeader(value = "accesstoken", required = false) String accessToken, @PathVariable Integer bankruptcyNo) {
        log.info("BankruptcyController_getAllExecutionByBankruptcy_start");
        List<ExecutionRes> bankruptcyExecutionResList = bankruptcyService.getAllExecutionByBankruptcy(accessToken, bankruptcyNo);
        log.info("BankruptcyController_getAllExecutionByBankruptcy_end: 파산 횟수에 따른 거래 내역 " + bankruptcyExecutionResList);
        return CommonResponse.success(bankruptcyExecutionResList);
    }
}
