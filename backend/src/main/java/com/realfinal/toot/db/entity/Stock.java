package com.realfinal.toot.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "stock")
@Entity
public class Stock {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry_id")
    private Industry industry;

    @Column(name = "stock_name", length = 50)
    @NotNull
    private String stockName;

    @Column(name = "total_stock")
    @NotNull
    private Long totalStock;

    @Column(name = "outline", length = 500)
    @NotNull
    private String outline;

    @Column(name = "eps")
    @NotNull
    private String eps;

    @Column(name = "bps")
    @NotNull
    private String bps;

    @Column(name = "image_url")
    @NotNull
    private String imageUrl;

    @Builder

    public Stock(String id, Industry industry, String stockName, Long totalStock, String outline,
        String eps, String bps, String imageUrl) {
        this.id = id;
        this.industry = industry;
        this.stockName = stockName;
        this.totalStock = totalStock;
        this.outline = outline;
        this.eps = eps;
        this.bps = bps;
        this.imageUrl = imageUrl;
    }
}
