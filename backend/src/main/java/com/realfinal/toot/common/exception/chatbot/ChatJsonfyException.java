package com.realfinal.toot.common.exception.chatbot;

import com.realfinal.toot.common.exception.ErrorCode;
import com.realfinal.toot.common.model.BaseException;

public class ChatJsonfyException extends BaseException {

    public ChatJsonfyException() {
        super(ErrorCode.CHAT_NOT_JSONFY);
    }

}
