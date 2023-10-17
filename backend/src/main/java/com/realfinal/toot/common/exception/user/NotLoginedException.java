package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NotLoginedException extends BaseException {

    public NotLoginedException() {super(ErrorCode.NOT_LOGINED);}

}
