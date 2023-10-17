package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class UnexpectedTokenException extends BaseException {

    public UnexpectedTokenException() {super(ErrorCode.UNEXPECTED_ERROR);}

}
