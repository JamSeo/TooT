package com.realfinal.toot.api.friend.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RankListRes {

    List<RankRes> list;
    RankRes myInfo;
    Integer myRank;

    @Builder
    public RankListRes(List<RankRes> list, RankRes myInfo, Integer myRank) {
        this.list = list;
        this.myInfo = myInfo;
        this.myRank = myRank;
    }
}
