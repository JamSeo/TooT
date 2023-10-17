package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KakaoTokenRequestException extends BaseException {
    public KakaoTokenRequestException() {super(ErrorCode.KAKAO_REQUEST_TOKEN);}

}
