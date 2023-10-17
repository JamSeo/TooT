package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NoRefreshTokenInCookieException extends BaseException {
    public NoRefreshTokenInCookieException() {super(ErrorCode.TOKEN_NOT_IN_COOKIE);}



}
