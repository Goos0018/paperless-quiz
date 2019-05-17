package com.example.paperlessquiz.answer;

import java.util.ArrayList;

public class RoundAnswers {
    private ArrayList<Answer> answersForRound;

    public RoundAnswers(int size) {
        this.answersForRound  = new ArrayList<Answer>();
        for (int i=0;i<size;i++)
        {
            answersForRound.add(i,new Answer(i));
        }
    }

    public ArrayList<Answer> getAnswersForRound() {
        return answersForRound;
    }

    public void setAnswersForRound(ArrayList<Answer> answersForRound) {
        this.answersForRound = answersForRound;
    }
}
