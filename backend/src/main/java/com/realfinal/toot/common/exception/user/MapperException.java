package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class MapperException extends BaseException {
    public MapperException() {super(ErrorCode.MAPPING_PROBLEM);}

}
