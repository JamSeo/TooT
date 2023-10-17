package com.realfinal.toot.api.bankruptcy.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AllBankruptcyRes {

    private Integer bankruptcyNo;
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime bankruptAt;
    private Long lastTotalAsset; // 총자산
    private Long netIncome; // 순수익
    private Double roi; // 수익률

    @Builder
    public AllBankruptcyRes(Integer bankruptcyNo, LocalDateTime bankruptAt, Long lastTotalAsset,
        Long netIncome, Double roi) {
        this.bankruptcyNo = bankruptcyNo;
        this.bankruptAt = bankruptAt;
        this.lastTotalAsset = lastTotalAsset;
        this.netIncome = netIncome;
        this.roi = roi;
    }
}
