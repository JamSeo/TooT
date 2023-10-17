package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {super(ErrorCode.TOKEN_NOT_VALID);}

}
