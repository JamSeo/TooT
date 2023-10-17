package com.realfinal.toot.api.stock.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ExecutionRes {

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dealAt;
    private Boolean isBought;
    private String stockId;
    private String stockName;
    private Integer amount;
    private Integer price;
    private Long totalPrice;

    @Builder
    public ExecutionRes(LocalDateTime dealAt, Boolean isBought, String stockId, String stockName,
        Integer amount, Integer price, Long totalPrice) {
        this.dealAt = dealAt;
        this.isBought = isBought;
        this.stockId = stockId;
        this.stockName = stockName;
        this.amount = amount;
        this.price = price;
        this.totalPrice = totalPrice;
    }
}
