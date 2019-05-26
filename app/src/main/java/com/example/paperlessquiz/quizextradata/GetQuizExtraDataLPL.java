package com.example.paperlessquiz.quizextradata;

import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class GetQuizExtraDataLPL implements ListParsedListener<QuizExtraData> {

    private Quiz quiz;
    private QuizExtraData quizExtraData;
    private ArrayList<QuizExtraData> list;

    public GetQuizExtraDataLPL(Quiz quiz) {
        //public GetQuizExtraDataLPL(QuizExtraData quizExtraData) {
        this.quizExtraData = new QuizExtraData();
        this.quiz = quiz;

    }

    public QuizExtraData getQuizExtraData() {
        return quizExtraData;
    }

    public void listParsed(List<QuizExtraData> list) {
        quizExtraData = new QuizExtraData(list.get(0));
        this.list = (ArrayList) list;
        if (!(quiz == null)) {
            quiz.setAdditionalData(quizExtraData);
        }
    }
}
