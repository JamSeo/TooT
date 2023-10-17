package com.realfinal.toot.api.stock.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StockReq {

    private String stockId;
    private Integer count;
}
