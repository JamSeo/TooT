package com.realfinal.toot.common.exception.quiz;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NoQuizDataException extends BaseException {
    public NoQuizDataException() {super(ErrorCode.DATABASE_EMPTY);}

}
