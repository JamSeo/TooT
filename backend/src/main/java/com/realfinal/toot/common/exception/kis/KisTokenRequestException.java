package com.realfinal.toot.common.exception.kis;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class KisTokenRequestException extends BaseException {
  public KisTokenRequestException() {
    super(ErrorCode.KIS_TOKEN_REQUEST_FAILED);
  }
}
