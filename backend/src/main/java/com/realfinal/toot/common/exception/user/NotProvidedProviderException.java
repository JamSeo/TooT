package com.realfinal.toot.common.exception.user;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class NotProvidedProviderException extends BaseException {

    public NotProvidedProviderException() {super(ErrorCode.NOT_PROVIDED_PROVIDER);}
}
