package com.realfinal.toot.api.stock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SpecificStockRes {

    private String stockName;
    private List<MinuteRes> minCandle;
    private List<DayWeekRes> dayCandle;
    private List<DayWeekRes> weekCandle;
    private String totalPrice;
    private Integer currentPrice;
    private Long totalStock;
    private String industryClass;
    private String wics;
    private Integer min52;
    private Integer max52;
    private String per;
    private String pbr;
    private Integer priceDifference;
    private Double rateDifference;
    private String outline;
    private Boolean interested;
    private String imageUrl;
    private Integer hold;

    @Builder
    public SpecificStockRes(String stockName, List<MinuteRes> minCandle, List<DayWeekRes> dayCandle,
        List<DayWeekRes> weekCandle, String totalPrice, Integer currentPrice, Long totalStock,
        String industryClass, String wics, Integer min52, Integer max52, String per, String pbr,
        Integer priceDifference, Double rateDifference, String outline, Boolean interested,
        String imageUrl, Integer hold) {
        this.stockName = stockName;
        this.minCandle = minCandle;
        this.dayCandle = dayCandle;
        this.weekCandle = weekCandle;
        this.totalPrice = totalPrice;
        this.currentPrice = currentPrice;
        this.totalStock = totalStock;
        this.industryClass = industryClass;
        this.wics = wics;
        this.min52 = min52;
        this.max52 = max52;
        this.per = per;
        this.pbr = pbr;
        this.priceDifference = priceDifference;
        this.rateDifference = rateDifference;
        this.outline = outline;
        this.interested = interested;
        this.imageUrl = imageUrl;
        this.hold = hold;
    }
}
