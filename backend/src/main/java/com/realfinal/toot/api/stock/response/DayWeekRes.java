package com.realfinal.toot.api.stock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DayWeekRes {

    private String date;
    private String startPrice;
    private String endPrice;
    private String bestPrice;
    private String worstPrice;
    private String amount;

    @Builder
    public DayWeekRes(String date, String startPrice, String endPrice, String bestPrice,
        String worstPrice, String amount) {
        this.date = date;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.bestPrice = bestPrice;
        this.worstPrice = worstPrice;
        this.amount = amount;
    }

    public void updateDate(String date) {
        this.date = date;
    }

    public void updateStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public void updateEndPrice(String endPrice) {
        this.endPrice = endPrice;
    }

    public void updateBestPrice(String bestPrice) {
        this.bestPrice = bestPrice;
    }

    public void updateWorstPrice(String worstPrice) {
        this.worstPrice = worstPrice;
    }

    public void updateAmount(String amount) {
        this.amount = amount;
    }
}
