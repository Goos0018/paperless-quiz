package com.example.paperlessquiz.quizlistdata;

import com.example.paperlessquiz.adapters.QuizBasicsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class GetQuizListDataLPL implements ListParsedListener<QuizListData> {

    private QuizBasicsAdapter quizBasicsAdapter;

    public GetQuizListDataLPL(QuizBasicsAdapter quizBasicsAdapter) {
        this.quizBasicsAdapter = quizBasicsAdapter;
    }

    public void listParsed(List<QuizListData> list) {
        quizBasicsAdapter.addAll(list);
    }
}
