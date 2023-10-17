package com.realfinal.toot.common.model;

import com.realfinal.toot.common.exception.ErrorCode;

public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * Errorcode 외에 별도의 메시지가 필요할 경우 추가하여 작성 가능
     */
    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
