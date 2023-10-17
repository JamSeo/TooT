package com.realfinal.toot.api.user.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRes {

    private final Long id;
    private final Long seedMoney;
    private final Long cash;
    private final String profileImage;
    private final String name;
    private final Integer bankruptcyNo;
    private final LocalDate lastQuizDate;
    private final LocalDateTime joinAt;
    private final Long totalValue;

    @Builder
    public UserRes(Long id, Long seedMoney, Long cash, String profileImage, String name,
        Integer bankruptcyNo, LocalDate lastQuizDate, LocalDateTime joinAt, Long totalValue) {
        this.id = id;
        this.seedMoney = seedMoney;
        this.cash = cash;
        this.profileImage = profileImage;
        this.name = name;
        this.bankruptcyNo = bankruptcyNo;
        this.lastQuizDate = lastQuizDate;
        this.joinAt = joinAt;
        this.totalValue = totalValue;
    }
}
