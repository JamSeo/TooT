package com.realfinal.toot.api.stock.service;

import com.realfinal.toot.api.stock.request.StockReq;
import com.realfinal.toot.api.stock.response.AllStockRes;
import com.realfinal.toot.api.stock.response.ExecutionRes;
import com.realfinal.toot.api.stock.response.InterestRes;
import com.realfinal.toot.api.stock.response.MyStockRes;
import com.realfinal.toot.api.stock.response.SpecificStockRes;
import com.realfinal.toot.api.stock.response.StockRankRes;
import com.realfinal.toot.api.stock.response.UserValueRes;
import java.util.List;

public interface StockService {

    Integer buyStock(String accessToken, StockReq stockReq);

    List<AllStockRes> showAll(String accessToken);

    UserValueRes calculateValue(String accessToken);

    List<StockRankRes> rankByVolume();

    List<InterestRes> showInterest(String accessToken);

    SpecificStockRes getStockInfo(String stockId, String accessToken);

    List<MyStockRes> myStocks(String accessToken);

    MyStockRes myStock(String accessToken, String stockId);

    List<ExecutionRes> myAllExecution(String accessToken);

    List<ExecutionRes> myExecution(String stockId, String accessToken);

    Integer sellStock(String accessToken, StockReq stockReq);

    Boolean addInterest(String stockId, String accessToken);

    Boolean cancelInterest(String stockId, String accessToken);
}
