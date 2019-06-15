package com.example.paperlessquiz.scores;

import com.example.paperlessquiz.MyApplication;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

public class GetScoresLPL implements ListParsedListener<Score> {

    private ArrayList<Score> allScores;

    public GetScoresLPL() {
        allScores =new ArrayList<>();
    }

    @Override
    public void listParsed(List<Score> list) {
        allScores = (ArrayList) list;
        //Update the central Quiz object
        if (MyApplication.theQuiz!=null){
            MyApplication.theQuiz.setAllScoresPerTeam(allScores);
        }
        else{

        }
    }
}
