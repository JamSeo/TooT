package com.realfinal.toot.api.quiz.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class QuizQuestionRes {

    private final List<QuizRes> quizList;
    private final int answerIndex;
    private final String answerString;

    @Builder
    public QuizQuestionRes(List<QuizRes> quizList, int answerIndex, String answerString) {
        this.quizList = quizList;
        this.answerIndex = answerIndex;
        this.answerString = answerString;
    }
}
