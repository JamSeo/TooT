package com.realfinal.toot.api.bankruptcy.service;

import com.realfinal.toot.api.bankruptcy.response.AllBankruptcyRes;
import com.realfinal.toot.api.bankruptcy.response.DetailBankruptcyRes;
import com.realfinal.toot.api.stock.response.ExecutionRes;
import java.util.List;

public interface BankruptcyService {

    Boolean isBankruptcyAvailable(String accessToken);

    void proceedBankrupt(String accessToken);

    List<AllBankruptcyRes> getAllBankruptcy(String accessToken);

    DetailBankruptcyRes getDetailBankruptcy(String accessToken, Integer bankruptcyNo);

    List<ExecutionRes> getAllExecutionByBankruptcy(String accessToken, Integer bankruptcyNo);

}
