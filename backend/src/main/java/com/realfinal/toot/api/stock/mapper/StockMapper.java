package com.realfinal.toot.api.stock.mapper;

import com.realfinal.toot.api.stock.response.*;
import com.realfinal.toot.common.exception.user.MapperException;
import com.realfinal.toot.db.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    @Mapping(source = "count", target = "hold")
    @Mapping(source = "price", target = "averagePrice")
    @Mapping(source = "user", target = "user")
    UserStock toUserStock(User user, Stock stock, Integer count, Integer price)
        throws MapperException;

    @Mapping(source = "count", target = "amount")
    @Mapping(source = "user", target = "user")
    Execution toExecution(Stock stock, User user, Integer price, Integer count, Boolean isBought)
        throws MapperException;

    @Mapping(source = "stock.id", target = "stockId")
    AllStockRes toAllStockRes(Stock stock, Integer currentPrice, String priceDifference,
        String rateDifference, Boolean liked) throws MapperException;

    UserValueRes toUserValueRes(User user, Long totalValue) throws MapperException;

    StockVolumeRes toStockVolumeRes(Stock stock, Long volume) throws MapperException;

    @Mapping(source = "stock.id", target = "stockId")
    StockRankRes toRankRes(Integer rank, Stock stock, Integer price, String rateDifference)
        throws MapperException;

    @Mapping(source = "stock.id", target = "stockId")
    InterestRes toInterestRes(Stock stock, Integer currentPrice, Integer priceDifference,
        Double rateDifference, Boolean isInterested) throws MapperException;


    @Mapping(source = "stock.industry.industryClass", target = "industryClass")
    @Mapping(source = "stock.industry.wics", target = "wics")
    SpecificStockRes toSpecificStockRes(Stock stock, List<MinuteRes> minCandle,
        List<DayWeekRes> dayCandle, List<DayWeekRes> weekCandle, String totalPrice,
        Integer currentPrice, Integer min52, Integer max52, String per, String pbr,
        Integer priceDifference, Double rateDifference, Boolean interested, Integer hold)
        throws MapperException;

    @Mapping(source = "stock.id", target = "stockId")
    MyStockRes toMyStockRes(Stock stock, Integer hold, Integer averagePrice, Integer currentPrice,
        Long totalPrice, Long profit, Double profitRate) throws MapperException;

    @Mapping(source = "execution.stock.id", target = "stockId")
    @Mapping(source = "execution.stock.stockName", target = "stockName")
    ExecutionRes toExecutionRes(Execution execution, Long totalPrice) throws MapperException;

    Interest toInterest(User user, Stock stock, Boolean isInterested) throws MapperException;

    MinuteRes toMinuteRes(String time, String price, String amount) throws MapperException;

    DayWeekRes toDayWeekRes(String date, String startPrice, String endPrice, String bestPrice,
        String worstPrice, String amount) throws MapperException;
}
