package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class RedisNotDeletedException extends BaseException {
    public RedisNotDeletedException() {super(ErrorCode.REDIS_NOT_DELETED);}

}
