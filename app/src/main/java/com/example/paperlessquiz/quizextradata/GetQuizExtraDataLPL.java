package com.example.paperlessquiz.quizextradata;

import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * This list in this LPL will always only contain one element, which is stored in the quizExtraData variable
 */
public class GetQuizExtraDataLPL implements ListParsedListener<QuizExtraData> {

    private QuizExtraData quizExtraData;

    public GetQuizExtraDataLPL() {

    }

    public QuizExtraData getQuizExtraData() {
        return quizExtraData;
    }

    public void listParsed(List<QuizExtraData> list) {
        quizExtraData = new QuizExtraData(list.get(0));
    }
}
