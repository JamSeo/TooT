package com.realfinal.toot.api.stock.response;

import com.realfinal.toot.db.entity.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StockVolumeRes {

    private Stock stock;
    private Long volume;

    @Builder

    public StockVolumeRes(Stock stock, Long volume) {
        this.stock = stock;
        this.volume = volume;
    }
}
