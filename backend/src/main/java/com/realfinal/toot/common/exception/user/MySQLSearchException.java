package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class MySQLSearchException extends BaseException {
    public MySQLSearchException() {super(ErrorCode.MYSQL_NO_DATA);}

}
