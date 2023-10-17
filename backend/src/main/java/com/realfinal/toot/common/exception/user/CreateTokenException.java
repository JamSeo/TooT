package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class CreateTokenException extends BaseException {
    public CreateTokenException() {super(ErrorCode.TOKEN_CREATE_FAILED);}

}
