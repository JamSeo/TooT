package com.realfinal.toot.common.exception.bankruptcy;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NoBankruptcyDataException extends BaseException {

    public NoBankruptcyDataException() {
        super(ErrorCode.MYSQL_NO_DATA);
    }
}
