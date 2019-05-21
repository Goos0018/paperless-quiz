package com.example.paperlessquiz.round;

import com.example.paperlessquiz.adapters.QuizRoundsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class GetRoundsLPL implements ListParsedListener<Round> {

    private Quiz quiz;
    private ArrayList<Round> rounds;

    public GetRoundsLPL(Quiz quiz) {
        rounds = new ArrayList<Round>();
        this.quiz = quiz;

    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void listParsed(List<Round> list) {
        rounds = (ArrayList) list;
        if (!(quiz == null)) {
            quiz.setRounds((ArrayList) list);
        }
    }

}
