package com.realfinal.toot.api.stock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AllStockRes {

    private String stockId;
    private String stockName;
    private Integer currentPrice;
    private String priceDifference;
    private String rateDifference;
    private Boolean liked;

    @Builder
    public AllStockRes(String stockId, String stockName, Integer currentPrice,
        String priceDifference, String rateDifference, Boolean liked) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.priceDifference = priceDifference;
        this.rateDifference = rateDifference;
        this.liked = liked;
    }
}
