package com.realfinal.toot.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "user_stock")
@Entity
public class UserStock extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "hold")
    @NotNull
    private Integer hold;

    @Column(name = "bankruptcy_no")
    @NotNull
    private Integer bankruptcyNo;

    @Column(name = "average_price")
    @NotNull
    private Integer averagePrice;

    @Builder
    public UserStock(User user, Stock stock, Integer hold, Integer bankruptcyNo,
        Integer averagePrice) {
        this.user = user;
        this.stock = stock;
        this.hold = hold;
        this.bankruptcyNo = bankruptcyNo;
        this.averagePrice = averagePrice;
    }

    public void updateHold(Integer hold) {
        this.hold = hold;
    }

    public void updateAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }
}
