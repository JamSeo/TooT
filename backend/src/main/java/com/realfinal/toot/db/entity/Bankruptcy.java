package com.realfinal.toot.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "bankruptcy")
@Entity
public class Bankruptcy extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 파산 횟수
    @Column(name = "bankruptcy_no")
    @NotNull
    private Integer bankruptcyNo;

    // 마지막 보유현금
    @Column(name = "last_cash")
    @NotNull
    private Long lastCash;

    // 마지막 시드머니
    @Column(name = "last_seed_money")
    @NotNull
    private Long lastSeedMoney;

    // 최종 총자산
    @Column(name = "last_total_asset")
    @NotNull
    private Long lastTotalAsset;

    // 순수익
    @Column(name = "net_income")
    @NotNull
    private Long netIncome;

    // 손익률(수익률)
    @Column(name = "roi")
    @NotNull
    private Double roi;

    // 파산 일시
    @Column(name = "bankrupt_at")
    @NotNull
    private LocalDateTime bankruptAt;

    @Builder
    public Bankruptcy(User user, Integer bankruptcyNo, Long lastCash, Long lastSeedMoney,
        Long lastTotalAsset, LocalDateTime bankruptAt) {
        this.user = user;
        this.bankruptcyNo = bankruptcyNo;
        this.lastCash = lastCash;
        this.lastSeedMoney = lastSeedMoney;
        this.lastTotalAsset = lastTotalAsset;
        this.roi = Double.valueOf(
            Math.round(100.0 * (this.lastTotalAsset - lastSeedMoney) / lastSeedMoney) / 100.0);
        this.netIncome = this.lastTotalAsset - lastSeedMoney;
        this.bankruptAt = bankruptAt;
    }
}
