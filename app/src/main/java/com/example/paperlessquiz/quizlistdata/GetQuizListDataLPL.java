package com.example.paperlessquiz.quizlistdata;

import com.example.paperlessquiz.adapters.QuizListAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class GetQuizListDataLPL implements ListParsedListener<QuizListData> {

    private QuizListAdapter quizListAdapter;

    public GetQuizListDataLPL(QuizListAdapter quizListAdapter) {
        this.quizListAdapter = quizListAdapter;
    }

    public void listParsed(List<QuizListData> list) {
        quizListAdapter.addAll(list);
    }
}
