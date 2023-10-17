package com.realfinal.toot.common.exception.stock;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class StockNotFoundException extends BaseException {

    public StockNotFoundException() {
        super(ErrorCode.DATA_NOT_FOUND);
    }
}
