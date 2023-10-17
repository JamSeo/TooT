package com.realfinal.toot.common.exception.quiz;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NoWordException extends BaseException {
    public NoWordException() {super(ErrorCode.DATABASE_EMPTY);}

}
