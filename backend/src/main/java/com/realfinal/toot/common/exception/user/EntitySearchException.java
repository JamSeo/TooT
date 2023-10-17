package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class EntitySearchException extends BaseException {
    public EntitySearchException() {super(ErrorCode.DATA_NOT_FOUND);}


}
