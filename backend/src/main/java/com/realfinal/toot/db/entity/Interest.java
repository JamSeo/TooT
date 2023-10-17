package com.realfinal.toot.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "interest")
@Entity
public class Interest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "is_interested")
    private Boolean isInterested;

    @Builder
    public Interest(User user, Stock stock, Boolean isInterested) {
        this.user = user;
        this.stock = stock;
        this.isInterested = isInterested;
    }

    public void setInterest(Boolean isInterested) {
        this.isInterested = isInterested;
    }
}
