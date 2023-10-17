package com.realfinal.toot.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@NoArgsConstructor
@Table(name = "execution")
@Entity
public class Execution extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    @NotNull
    private Integer price;

    @Column(name = "amount")
    @NotNull
    private Integer amount;

    @Column(name = "is_bought")
    @NotNull
    private Boolean isBought;

    @Column(name = "deal_at")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private LocalDateTime dealAt;

    @Column(name = "bankruptcy_no")
    @NotNull
    private Integer bankruptcyNo;

    @Builder
    public Execution(Stock stock, User user, Integer price, Integer amount, Boolean isBought,
        Integer bankruptcyNo) {
        this.stock = stock;
        this.user = user;
        this.price = price;
        this.amount = amount;
        this.isBought = isBought;
        this.bankruptcyNo = bankruptcyNo;
    }
}
