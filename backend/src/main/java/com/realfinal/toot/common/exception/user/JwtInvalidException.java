package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class JwtInvalidException extends BaseException {

    public JwtInvalidException() {super(ErrorCode.TOKEN_NOT_VALID);}

}
