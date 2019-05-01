package com.example.paperlessquiz.quizgetter;

import com.example.paperlessquiz.google.adapter.ListParsedListener;

import java.util.List;

public class AddQuizGetterToAdapterListParsedListener implements ListParsedListener<QuizGetter> {

    private QuizGetterAdapter quizGetterAdapter;

    public AddQuizGetterToAdapterListParsedListener(QuizGetterAdapter quizGetterAdapter) {
        this.quizGetterAdapter = quizGetterAdapter;
    }

    public void listParsed(List<QuizGetter> list) {
        quizGetterAdapter.addAll(list);
    }
}
