package com.realfinal.toot.common.exception.kis;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KisApiCallTooManyException extends BaseException {
    public KisApiCallTooManyException() {super(ErrorCode.KIS_API_CALL_TOO_MANY);}
}
