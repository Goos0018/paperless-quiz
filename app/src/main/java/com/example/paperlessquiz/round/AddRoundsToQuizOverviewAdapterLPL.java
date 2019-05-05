package com.example.paperlessquiz.round;

import com.example.paperlessquiz.adapters.QuizOverviewAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddRoundsToQuizOverviewAdapterLPL implements ListParsedListener<Round> {

    private QuizOverviewAdapter quizOverviewAdapter;

    public AddRoundsToQuizOverviewAdapterLPL(QuizOverviewAdapter quizOverviewAdapter) {
        this.quizOverviewAdapter = quizOverviewAdapter;
    }

    public void listParsed(List<Round> list) {
        quizOverviewAdapter.addAll(list);
    }
}
