package com.realfinal.toot.api.user.mapper;

import com.realfinal.toot.api.user.request.KakaoUserInfoReq;
import com.realfinal.toot.api.user.request.UserReq;
import com.realfinal.toot.api.user.response.UserRes;
import com.realfinal.toot.common.exception.user.MapperException;
import com.realfinal.toot.db.entity.User;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "seedMoney", ignore = true)
    @Mapping(target = "cash", ignore = true)
    @Mapping(target = "bankruptcyNo", ignore = true)
    @Mapping(target = "lastQuizDate", ignore = true)
    User userReqToUser(UserReq userReq) throws MapperException;

    default UserReq kakaoUserInfoReqToUserReq(KakaoUserInfoReq kakaoUserInfoReq)
            throws MapperException {
        return UserReq.builder()
                .providerId(String.valueOf(kakaoUserInfoReq.getProviderId()))
                .name(kakaoUserInfoReq.getNickName())
                .profileImage(kakaoUserInfoReq.getImageUrl())
                .build();
    }

    UserRes userToUserRes(User user, Long totalValue) throws MapperException;

}
