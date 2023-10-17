package com.realfinal.toot.api.bankruptcy.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DetailBankruptcyRes {

    private Integer bankruptcyNo;
    private Long lastCash; // 마지막 보유 현금
    private Long lastSeedMoney; // 마지막 시드 머니
    private Long netIncome; // 마지막 순수익
    private Double roi; // 마지막 수익률
    private Long lastTotalAsset; // 마지막 총 자산
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime bankruptAt;

    @Builder
    public DetailBankruptcyRes(Integer bankruptcyNo, Long lastCash, Long lastSeedMoney,
        Long lastTotalAsset, Long netIncome, Double roi, LocalDateTime bankruptAt) {
        this.bankruptcyNo = bankruptcyNo;
        this.lastCash = lastCash;
        this.lastSeedMoney = lastSeedMoney;
        this.netIncome = netIncome;
        this.roi = roi;
        this.lastTotalAsset = lastTotalAsset;
        this.bankruptAt = bankruptAt;
    }
}
