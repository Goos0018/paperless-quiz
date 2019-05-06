package com.example.paperlessquiz.round;

import com.example.paperlessquiz.adapters.QuizRoundsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddRoundsToQuizRoundsAdapterLPL implements ListParsedListener<Round> {

    private QuizRoundsAdapter AddRoundsRoQuizRoundsAdapterLPL;

    public AddRoundsToQuizRoundsAdapterLPL(QuizRoundsAdapter quizRoundsAdapter) {
        this.AddRoundsRoQuizRoundsAdapterLPL = quizRoundsAdapter;
    }

    public void listParsed(List<Round> list) {
        AddRoundsRoQuizRoundsAdapterLPL.addAll(list);
    }
}
