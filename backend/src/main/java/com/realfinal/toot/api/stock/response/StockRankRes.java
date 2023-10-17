package com.realfinal.toot.api.stock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StockRankRes {

    private Integer rank;
    private String stockId;
    private String stockName;
    private Integer price;
    private String rateDifference;

    @Builder
    public StockRankRes(Integer rank, String stockId, String stockName, Integer price,
        String rateDifference) {
        this.rank = rank;
        this.stockId = stockId;
        this.stockName = stockName;
        this.price = price;
        this.rateDifference = rateDifference;
    }
}
