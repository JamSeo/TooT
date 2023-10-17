package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KakaoHTTPProtocolException extends BaseException {
    public KakaoHTTPProtocolException() {super(ErrorCode.KAKAO_PARSE_DATA);}

}
