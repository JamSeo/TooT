package com.realfinal.toot.api.friend.mapper;

import com.realfinal.toot.api.friend.request.OauthFriendInfoReq;
import com.realfinal.toot.api.friend.response.RankListRes;
import com.realfinal.toot.api.friend.response.RankRes;
import com.realfinal.toot.common.exception.user.MapperException;
import com.realfinal.toot.db.entity.User;
import java.util.List;
import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendMapper {

    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);

    default OauthFriendInfoReq jsonObjectToFriendInfoReq(JSONObject jsonObject)
            throws MapperException {
        return OauthFriendInfoReq.builder().
                profile_nickname(jsonObject.getString("profile_nickname"))
                .profile_thumbnail_image(jsonObject.getString("profile_thumbnail_image"))
                .id(jsonObject.getLong("id")).build();
    }

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "name", source = "oauthFriendInfoReq.profile_nickname")
    @Mapping(target = "profileImage", source = "oauthFriendInfoReq.profile_thumbnail_image")
    RankRes toRankRes(OauthFriendInfoReq oauthFriendInfoReq, User user,
            Long netProfit) throws MapperException;

    RankRes toRankRes(User user, Long netProfit) throws MapperException;

    default RankListRes toRankListRes(List<RankRes> rank, RankRes rankRes, int index)
            throws MapperException {
        return RankListRes.builder()
                .list(rank)
                .myInfo(rankRes)
                .myRank(index)
                .build();
    }
}
