package com.realfinal.toot.common.exception.quiz;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NotEnoughQuestion extends BaseException {
    public NotEnoughQuestion() {super(ErrorCode.NO_DATA);}

}
