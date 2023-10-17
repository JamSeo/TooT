package com.realfinal.toot.api.friend.controller;

import com.realfinal.toot.api.friend.response.RankListRes;
import com.realfinal.toot.api.friend.service.FriendService;
import com.realfinal.toot.common.model.CommonResponse;
import com.realfinal.toot.common.util.KakaoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/rank")
@RestController
public class FriendController {

    private final FriendService friendService;
    private final KakaoUtil kakaoUtil;

    /**
     * 카카오 친구 랭킹 리스트 가져오기
     *
     * @param accessToken id 확인용
     * @return 친구 랭킹 리스트, 내 정보, 내 등수
     */
    @GetMapping("/list/kakao")
    public CommonResponse<?> getFriendList(
            @RequestHeader(value = "accesstoken") String accessToken) {
        log.info("FriendController_getFriendList_start");
        RankListRes rankResList = friendService.getFriendList(accessToken);
        log.info("FriendController_getFriendList_end");
        return CommonResponse.success(rankResList);
    }

    /**
     * 전체 랭킹 가져오기
     *
     * @param accessToken id 확인용
     * @return 전체 랭킹 리스트, 내 정보, 내 등수
     */
    @GetMapping("/list")
    public CommonResponse<?> getRank(
            @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("FriendController_getRank_start");
        RankListRes rankResList = friendService.getRank(accessToken);
        log.info("FriendController_getRank_end");
        return CommonResponse.success(rankResList);
    }

    /**
     * 인가코드 재발급
     *
     * @param code        친구 목록 조회 동의 인가코드
     * @param accessToken 내 id 파악 위해
     * @return accesstoken 재발급 성공시 "success"
     */
    @GetMapping("/reissue/kakao")
    public CommonResponse<?> reissue(@RequestParam String code,
            @RequestHeader(value = "accesstoken", required = false) String accessToken) {
        log.info("FriendController_reissue_start");
        kakaoUtil.reissue(code, "kakao", accessToken);
        log.info("FriendController_reissue_end: success to reissue kakao OAuth token");
        return CommonResponse.success("success");
    }
}
