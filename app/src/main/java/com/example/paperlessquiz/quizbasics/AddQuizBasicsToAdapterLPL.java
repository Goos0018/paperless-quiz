package com.example.paperlessquiz.quizbasics;

import com.example.paperlessquiz.adapters.QuizBasicsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddQuizBasicsToAdapterLPL implements ListParsedListener<QuizBasics> {

    private QuizBasicsAdapter quizBasicsAdapter;

    public AddQuizBasicsToAdapterLPL(QuizBasicsAdapter quizBasicsAdapter) {
        this.quizBasicsAdapter = quizBasicsAdapter;
    }

    public void listParsed(List<QuizBasics> list) {
        quizBasicsAdapter.addAll(list);
    }
}
