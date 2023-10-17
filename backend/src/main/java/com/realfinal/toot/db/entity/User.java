package com.realfinal.toot.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Entity
@Table(name = "\"user\"")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    //    provider_id    varchar(255)                       not null,
    @Column(name = "provider_id")
    @NotNull
    private String providerId;
    //    seed_money     bigint   default 1000000           not null,
    @Column(name = "seed_money")
    @NotNull
    private Long seedMoney = 1000000L;
    //    cash           bigint   default 1000000           not null,
    @Column(name = "cash")
    @NotNull
    private Long cash = 1000000L;
    //    profile_image  varchar(255)                       null,
    @Column(name = "profile_image")
    private String profileImage;
    //    name           varchar(255)                       not null,
    @Column(name = "name")
    @NotNull
    private String name;
    //    bankruptcy_no  int      default 0                 not null,
    @Column(name = "bankruptcy_no")
    @NotNull
    private Integer bankruptcyNo = 0;
    //    last_quiz_date date                               null,
    @Column(name = "last_quiz_date")
    private LocalDate lastQuizDate;
    //    join_at        datetime default CURRENT_TIMESTAMP not null,
    @Column(name = "join_at", insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private LocalDateTime joinAt;


    @Builder
    public User(String providerId, Long seedMoney, Long cash, String profileImage, String name,
            Integer bankruptcyNo, LocalDate lastQuizDate) {
        this.providerId = providerId;
        this.seedMoney = seedMoney;
        this.cash = cash;
        this.profileImage = profileImage;
        this.name = name;
        this.bankruptcyNo = bankruptcyNo;
        this.lastQuizDate = lastQuizDate;
    }

    public void setLastQuizDate(LocalDate currentDate) {
        this.lastQuizDate = currentDate;
    }

    public void updateQuizResult() {
        this.seedMoney += 10000L;
        this.cash += 10000L;
    }

    public void updateCash(Long cash) {
        this.cash = cash;
    }

    public void resetAfterBankruptcy() {
        this.bankruptcyNo += 1;
        this.seedMoney = 900000L;
        this.cash = 900000L;
    }
}
