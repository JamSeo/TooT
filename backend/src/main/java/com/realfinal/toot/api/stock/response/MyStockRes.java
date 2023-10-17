package com.realfinal.toot.api.stock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MyStockRes {

    private String stockId;
    private String stockName;
    private Integer hold;
    private Integer averagePrice;
    private Integer currentPrice;
    private Long totalPrice;
    private Long profit;
    private Double profitRate;

    @Builder
    public MyStockRes(String stockId, String stockName, Integer hold, Integer averagePrice,
        Integer currentPrice, Long totalPrice, Long profit, Double profitRate) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.hold = hold;
        this.averagePrice = averagePrice;
        this.currentPrice = currentPrice;
        this.totalPrice = totalPrice;
        this.profit = profit;
        this.profitRate = profitRate;
    }
}
