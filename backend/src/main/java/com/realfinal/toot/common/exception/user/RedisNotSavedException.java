package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class RedisNotSavedException extends BaseException {

    public RedisNotSavedException() {super(ErrorCode.REDIS_NOT_SAVED);}
}
