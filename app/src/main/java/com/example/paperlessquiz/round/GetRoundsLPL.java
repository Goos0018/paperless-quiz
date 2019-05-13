package com.example.paperlessquiz.round;

import com.example.paperlessquiz.adapters.QuizRoundsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

public class GetRoundsLPL implements ListParsedListener<Round> {

    //private QuizRoundsAdapter AddRoundsRoQuizRoundsAdapterLPL;
    private ArrayList<Round> rounds;

    public GetRoundsLPL() {
        //this.AddRoundsRoQuizRoundsAdapterLPL = quizRoundsAdapter;
        rounds = new ArrayList<Round>();
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void listParsed(List<Round> list)
    {
        //AddRoundsRoQuizRoundsAdapterLPL.addAll(list);
        for (int i=0;i < list.size();i++)
        {
            rounds.add(i,list.get(i));
        }
    }
}
