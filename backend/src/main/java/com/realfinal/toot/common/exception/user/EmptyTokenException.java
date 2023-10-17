package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class EmptyTokenException extends BaseException {
    public EmptyTokenException() {super(ErrorCode.TOKEN_NOT_VALID);}


}
