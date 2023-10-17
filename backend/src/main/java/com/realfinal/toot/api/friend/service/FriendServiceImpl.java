package com.realfinal.toot.api.friend.service;

import com.realfinal.toot.api.friend.mapper.FriendMapper;
import com.realfinal.toot.api.friend.request.OauthFriendInfoReq;
import com.realfinal.toot.api.friend.response.RankListRes;
import com.realfinal.toot.api.friend.response.RankRes;
import com.realfinal.toot.common.exception.user.KakaoIOException;
import com.realfinal.toot.common.exception.user.KakaoTokenRequestException;
import com.realfinal.toot.common.exception.user.MySQLSearchException;
import com.realfinal.toot.common.util.JwtProviderUtil;
import com.realfinal.toot.common.util.PriceUtil;
import com.realfinal.toot.common.util.RedisUtil;
import com.realfinal.toot.db.entity.User;
import com.realfinal.toot.db.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FriendServiceImpl implements FriendService {

    private final JwtProviderUtil jwtProviderUtil;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;
    private final PriceUtil priceUtil;

    /**
     * 카카오 친구 랭킹 카카오에서 받아서 반환
     *
     * @param accessToken 내 유저 id
     * @return 랭킹 리스트, 내 등수, 내 정보
     */
    @Override
    public RankListRes getFriendList(String accessToken) {
        log.info("FriendServiceImpl_getFriendList_start: " + accessToken);
        Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
        String kakaoAccessToken = redisUtil.getData(userId.toString());

        final String FRIENDS_LIST_SERVICE_URL = "https://kapi.kakao.com/v1/api/talk/friends";
        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet get = new HttpGet(FRIENDS_LIST_SERVICE_URL);
        List<RankRes> kakaoFriendList = new ArrayList<>();

        // Authorization 헤더 설정. 카카오에 친구 목록 요청.
        get.addHeader("Authorization", "Bearer " + kakaoAccessToken);

        try {
            final HttpResponse response = client.execute(get);
            final int responseCode = response.getStatusLine().getStatusCode();

            if (responseCode == 200) { // 이미 사용자가 친구 요청에 동의한경우.
                JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                JSONArray elements = (JSONArray) jsonObject.get("elements");
                //받아온 응답에서 친구 목록 하나씩 빼서 반환할 리스트에 담기 작업
                for (Object element : elements) {
                    JSONObject friend = (JSONObject) element;
                    OauthFriendInfoReq oauthFriendInfoReq = FriendMapper.INSTANCE.jsonObjectToFriendInfoReq(
                        friend);
                    User user = userRepository.findByProviderId(
                        oauthFriendInfoReq.getId().toString());
                    //순이익 계산.
                    Long netProfit = priceUtil.calNetProfit(user.getId());
                    RankRes kakaoFriendRes = FriendMapper.INSTANCE.toRankRes(
                        oauthFriendInfoReq, user, netProfit);
                    kakaoFriendList.add(kakaoFriendRes);
                }
                //랭킹을 조회 요청한 유저의 정보
                User user = userRepository.findById(userId).orElseThrow(MySQLSearchException::new);
                Long netProfit = priceUtil.calNetProfit(user.getId());
                RankRes kakaoFriendRes = FriendMapper.INSTANCE.toRankRes(
                    user, netProfit);
                kakaoFriendList.add(kakaoFriendRes);

                kakaoFriendList.sort(
                    Comparator.comparing(RankRes::getNetProfit)
                        .reversed()  // 내림차순으로 netProfit 정렬
                        .thenComparing(
                            RankRes::getBankruptcyNo)    // netProfit이 같을 경우, 오름차순으로 bankruptcyNo 정렬
                        .thenComparing(RankRes::getId));    // bankruptcyNo가 같을 경우, 오름차순으로 id 정렬

                Integer index = -1;
                //랭킹 전체 정렬 후, 내 등수 찾아서 프론트에 반환할 response mapping
                if (kakaoFriendRes != null && !kakaoFriendList.isEmpty()) {
                    int left = 0, right = kakaoFriendList.size() - 1, mid = (left + right) >> 1;

                    while (left < right) {
                        RankRes midRankRes = kakaoFriendList.get(mid);

                        if (midRankRes.getNetProfit() > kakaoFriendRes.getNetProfit()) {
                            left = mid + 1;
                        } else if (midRankRes.getNetProfit() < kakaoFriendRes.getNetProfit()) {
                            right = mid - 1;
                        } else if (midRankRes.getBankruptcyNo()
                            < kakaoFriendRes.getBankruptcyNo()) {
                            left = mid + 1;
                        } else if (midRankRes.getBankruptcyNo()
                            > kakaoFriendRes.getBankruptcyNo()) {
                            right = mid - 1;
                        } else if (midRankRes.getId() < kakaoFriendRes.getId()) {
                            left = mid + 1;
                        } else if (midRankRes.getId() > kakaoFriendRes.getId()) {
                            right = mid - 1;
                        } else {
                            break;
                        }

                        mid = (left + right) >> 1;
                    }

                    if (kakaoFriendList.get(mid).getId().equals(kakaoFriendRes.getId())) {
                        index = mid;
                    }
                }

                RankListRes rankListRes = FriendMapper.INSTANCE.toRankListRes(
                    kakaoFriendList, kakaoFriendRes, index);
                log.info("FriendServiceImpl_getFriendList_end: " + rankListRes);
                return rankListRes;
            } else {
                // 오류 처리. 동의 안한 사용자인 경우라 에러가 아님.
                log.info(
                    "FriendServiceImpl_getFriendList_end: Failed to get friends list, need to reissue token");
                //재요청 필요. reissue() 호출 위한 error 프론트에 던지기
                throw new KakaoTokenRequestException();
            }
        } catch (IOException e) {
            // 예외 처리
            log.error("FriendServiceImpl_getFriendList_mid: Unexpected Exception occurred ");
            throw new KakaoIOException();
        }

    }

    /**
     * 전체 랭킹 반환
     *
     * @param accessToken 내 유저 id
     * @return 전체 랭킹, 내 등수, 내 정보
     */
    @Override
    public RankListRes getRank(String accessToken) {
        log.info("FriendServiceImpl_getRank_start");

        List<RankRes> rankList = priceUtil.getRankList();
        Map<Long, Integer> userRank = priceUtil.getUserRank();

        RankRes rankRes = null;
        Integer index = -1;
        if (accessToken != null && !accessToken.isEmpty()) {
            Long userId = jwtProviderUtil.getUserIdFromToken(accessToken);
            User user = userRepository.getReferenceById(userId);
            if (user != null) {
                Long netProfit = priceUtil.calNetProfit(user.getId());
                rankRes = FriendMapper.INSTANCE.toRankRes(user, netProfit);

                index = userRank.getOrDefault(userId, -1);
            }
        }

        RankListRes rankListRes = FriendMapper.INSTANCE.toRankListRes(
            rankList, rankRes, index);

        log.info("FriendServiceImpl_getRank_end");

        return rankListRes;
    }
}