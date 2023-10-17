package com.realfinal.toot.api.friend.service;

import com.realfinal.toot.api.friend.response.RankListRes;

public interface FriendService {

    RankListRes getFriendList(String accessToken);

    RankListRes getRank(String accessToken);

}
