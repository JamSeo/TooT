package com.realfinal.toot.common.exception.kis;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KisApiCallDeniedException extends BaseException {
    public KisApiCallDeniedException() {
        super(ErrorCode.KIS_API_CALL_DENIED);
    }
}
