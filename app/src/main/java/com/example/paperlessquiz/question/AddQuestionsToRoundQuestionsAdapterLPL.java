package com.example.paperlessquiz.question;

import com.example.paperlessquiz.adapters.RoundQuestionsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddQuestionsToRoundQuestionsAdapterLPL implements ListParsedListener<Question> {

    private RoundQuestionsAdapter roundQuestionsAdapter;
    private Question[] questionsList;

    public AddQuestionsToRoundQuestionsAdapterLPL(RoundQuestionsAdapter roundQuestionsAdapter){
        this.roundQuestionsAdapter=roundQuestionsAdapter;
    }

    @Override
    public void listParsed(List<Question> list) {
        roundQuestionsAdapter.addAll(list);
    }
}
