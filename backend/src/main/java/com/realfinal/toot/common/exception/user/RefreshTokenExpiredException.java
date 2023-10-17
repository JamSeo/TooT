package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class RefreshTokenExpiredException extends BaseException {
    public RefreshTokenExpiredException() {super(ErrorCode.TOKEN_NOT_VALID);}


}
