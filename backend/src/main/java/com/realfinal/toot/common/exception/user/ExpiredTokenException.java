package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class ExpiredTokenException extends BaseException {
    public ExpiredTokenException() {super(ErrorCode.TOKEN_NOT_VALID);}


}
