package com.realfinal.toot.api.quiz.service;

import com.realfinal.toot.api.quiz.response.QuizQuestionRes;

public interface QuizService {

    Boolean isQuizTodayAvailable(String accessToken);

    QuizQuestionRes getQuestion();

    void saveQuizResult(String accessToken);
}
