package com.realfinal.toot.api.quiz.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuizRes {

    private final String word;
    private final String meaning;

    @Builder
    public QuizRes(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
}
