package com.realfinal.toot.api.stock.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserValueRes {

    private Long seedMoney;
    private Long cash;
    private Long totalValue;

    @Builder
    public UserValueRes(Long seedMoney, Long cash, Long totalValue) {
        this.seedMoney = seedMoney;
        this.cash = cash;
        this.totalValue = totalValue;
    }
}
