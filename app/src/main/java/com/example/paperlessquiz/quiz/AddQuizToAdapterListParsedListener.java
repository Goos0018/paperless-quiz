package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.google.adapter.ListParsedListener;

import java.util.List;

public class AddQuizToAdapterListParsedListener implements ListParsedListener<Quiz> {

    private QuizAdapter quizAdapter;

    public AddQuizToAdapterListParsedListener(QuizAdapter quizAdapter) {
        this.quizAdapter = quizAdapter;
    }

    public void listParsed(List<Quiz> list) {
        quizAdapter.addAll(list);
    }
}
