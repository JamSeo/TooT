package com.realfinal.toot.api.user.service;

import com.realfinal.toot.api.stock.response.UserValueRes;
import com.realfinal.toot.api.user.mapper.UserMapper;
import com.realfinal.toot.api.user.response.OauthTokenRes;
import com.realfinal.toot.api.user.response.UserRes;
import com.realfinal.toot.common.exception.user.InvalidTokenException;
import com.realfinal.toot.common.exception.user.KakaoTokenRequestException;
import com.realfinal.toot.common.exception.user.MySQLSearchException;
import com.realfinal.toot.common.exception.user.NotProvidedProviderException;
import com.realfinal.toot.common.util.JwtProviderUtil;
import com.realfinal.toot.common.util.KakaoUtil;
import com.realfinal.toot.common.util.PriceUtil;
import com.realfinal.toot.common.util.RedisUtil;
import com.realfinal.toot.db.entity.User;
import com.realfinal.toot.db.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final JwtProviderUtil jwtProviderUtil;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private final KakaoUtil kakaoUtil;
    private final PriceUtil priceUtil;

    /**
     * 로그인 서비스 - kakao 로그인 kakao로부터 Oauth Token 받기
     *
     * @param authorizeCode 프론트에서 받은 인가코드
     * @param provider      카카오
     * @return refresh token
     */
    @Override
    public String login(String authorizeCode, String provider) {
        log.info("UserServiceImpl_login_start:\nauthorizeCode: " + authorizeCode + "\nprovider: "
            + provider);
        if (provider.equals("kakao")) {
            // 받은 인가코드로 다시 kakao에게 oauth Token 요청 -> 받기
            OauthTokenRes jsonToken = kakaoUtil.getOauthTokens(authorizeCode, "kakao");

            if (jsonToken.getError() != null) { // 에러가 (담겨)있다면
                log.info("UserServiceImpl_login_end: kakao 데이터 요청, 에러 반환, 에러코드: "
                    + jsonToken.getErrorCode() + " " + jsonToken.getErrorDescription());
                throw new KakaoTokenRequestException();
            }

            // 로그인한적 없는 ID이면 회원가입 후 userId 반환
            String userId = kakaoUtil.getUserIdAndRegistIfNot("kakao", jsonToken);
            try {
                // JWT refreshToken 생성
                String refreshToken = jwtProviderUtil.createRefreshToken();

                // redis에 JwtRefreshToken, oauthAccessToken 저장
                saveTokens(userId, refreshToken,
                    jsonToken.getAccessToken());
                return refreshToken;
            } catch (Exception e) {
                log.info("UserServiceImpl_login_mid: failed to login");
            }

        }
        throw new NotProvidedProviderException();
    }

    /**
     * 로그아웃. 토큰 삭제
     *
     * @param refreshToken refreshToken
     */
    @Transactional
    public void logout(String refreshToken) {
        log.info("UserServiceImpl_logout_start: " + refreshToken);
        redisUtil.deleteData(refreshToken);
        log.info("UserServiceImpl_logout_end: redis key deleted");
    }

    /**
     * 유저 정보 조회. access token으로 확인. 유효하지 않으면 에러.
     *
     * @param accessToken accessToken
     * @return 유저 정보
     */
    @Override
    public UserRes getUserInfo(String accessToken) {
        log.info("UserServiceImpl_getUserInfo_start: " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);

        if (jwtProviderUtil.validateToken(accessToken)) {
            User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
            UserValueRes userValueRes = priceUtil.calculateUserValue(userId);
            UserRes userRes = UserMapper.INSTANCE.userToUserRes(user, userValueRes.getTotalValue());
            log.info("UserServiceImpl_getUserInfo_end: " + userRes.toString());
            return userRes;
        }
        throw new InvalidTokenException();
    }

    /**
     * refresh token으로 access token 재발급
     *
     * @param refreshToken refreshToken
     * @return 새 accesstoken
     */
    @Override
    public String recreateAccessToken(String refreshToken) {
        log.info("UserServiceImpl_recreateAccessToken_start: " + refreshToken);
        String newAccessToken = jwtProviderUtil.recreateAccessToken(refreshToken);
        log.info("UserServiceImpl_recreateAccessToken_end: " + newAccessToken);
        return newAccessToken;
    }

    /**
     * 생성된 토큰 레디스에 저장
     *
     * @param userId               토큰 userId
     * @param refreshJWTToken  JWT refresh token
     * @param oauthAccessToken oauth access token
     */
    @Override
    public void saveTokens(String userId, String refreshJWTToken, String oauthAccessToken) {
        log.info("UserServiceImpl_saveTokens_start: " + userId + " " + refreshJWTToken + " "
            + oauthAccessToken);

        redisUtil.setDataWithExpire(refreshJWTToken, userId, 1209600000L);
        redisUtil.setDataWithExpire(userId, oauthAccessToken, 31536000L);

        log.info("UserServiceImpl_saveTokens_end: token saved");
    }

    /**
     * 인터셉터에서 accessToken의 payload에 저장된 userId가 DB에 존재하는지 확인
     *
     * @param userId accessToken payload에 담겨있던 userId
     * @return DB 존재여부
     */
    @Override
    public Boolean isUser(Long userId) {
        log.info("UserServiceImpl_isUser_start: " + userId);

        boolean isUser = userRepository.existsById(userId);
        log.info("UserServiceImpl_isUser_end: " + isUser);
        return isUser;
    }

    /**
     * 인터셉터에서 로그인 된 사용자인지 확인. 쿠키에 있는 refresh token과 redis에 있는 refresh token이 같은건지 확인
     *
     * @param refreshToken 리프레시 토큰
     * @return 결과가 로그아웃이다 판단하면 true, 로그인 된 상태면 false
     */
    @Override
    public Boolean isLogout(String refreshToken) {
        log.info("UserServiceImpl_isLogout_start: " + refreshToken);
        String data = redisUtil.getData(refreshToken);
        log.info("UserServiceImpl_isLogout_end: isLogout?" + (data == null));
        return data == null;
    }
}
