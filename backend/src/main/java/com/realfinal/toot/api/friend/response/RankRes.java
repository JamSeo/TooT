package com.realfinal.toot.api.friend.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RankRes {
    @JsonIgnore
    private Long id;
    private final String name;
    private final String profileImage;
    private final Integer bankruptcyNo;
    private final Long netProfit; //순이익

    @Builder
    public RankRes(Long id, String profileImage, String name, Integer bankruptcyNo,
            Long netProfit) {
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
        this.bankruptcyNo = bankruptcyNo;
        this.netProfit = netProfit;
    }
}
