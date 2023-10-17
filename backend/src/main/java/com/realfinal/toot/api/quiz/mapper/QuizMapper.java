package com.realfinal.toot.api.quiz.mapper;


import com.realfinal.toot.api.quiz.response.QuizQuestionRes;
import com.realfinal.toot.api.quiz.response.QuizRes;
import com.realfinal.toot.common.exception.user.MapperException;
import com.realfinal.toot.db.entity.Word;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuizMapper {

    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    QuizRes wordToQuizRes(Word word) throws MapperException;

    @Mapping(source = "answerIndex", target = "answerIndex")
    @Mapping(source = "answerString", target = "answerString")
    @Mapping(source = "quizResList", target = "quizList")
    QuizQuestionRes toQuizQuestionRes(Integer answerIndex, String answerString,
            List<QuizRes> quizResList) throws MapperException;
}
