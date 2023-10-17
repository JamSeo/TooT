package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KakaoIOException extends BaseException {
    public KakaoIOException() {super(ErrorCode.KAKAO_PARSE_DATA);}

}
