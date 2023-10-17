package com.realfinal.toot.api.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Ibubble {

    private final String type;
    private final String message;
    private final String url;
    private final Boolean speaker;

    @JsonCreator
    public Ibubble(@JsonProperty("type") String type,
            @JsonProperty("message") String message,
            @JsonProperty("url") String url,
            @JsonProperty("speaker") Boolean speaker) {
        this.type = type;
        this.message = message;
        this.url = url;
        this.speaker = speaker;
    }
}

