package com.example.paperlessquiz.quizextradata;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

public class GetQuizExtraDataLPL implements ListParsedListener<QuizExtraData> {

    private QuizExtraData quizExtraData;
    private ArrayList<QuizExtraData> list;

    public GetQuizExtraDataLPL() {
    //public GetQuizExtraDataLPL(QuizExtraData quizExtraData) {
        this.quizExtraData = new QuizExtraData();

    }

    public QuizExtraData getQuizExtraData() {
        return quizExtraData;
    }

    public void listParsed(List<QuizExtraData> list) {
        quizExtraData = new QuizExtraData(list.get(0));
        this.list = (ArrayList)list;

    }

    //@Override
    public List<QuizExtraData> getData() {
        return this.list;
    }
}
