package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KakaoJSONEncodingException extends BaseException {
    public KakaoJSONEncodingException() {super(ErrorCode.KAKAO_PARSE_DATA);}

}
