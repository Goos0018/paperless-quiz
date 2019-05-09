package com.example.paperlessquiz.question;

import com.example.paperlessquiz.adapters.RoundQuestionsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddQuestionsToRoundQuestionsAdapterLPL implements ListParsedListener<Question> {

    private RoundQuestionsAdapter roundQuestionsAdapter;
    private QuestionsList questionsList;
    private String answer1;

    public AddQuestionsToRoundQuestionsAdapterLPL(RoundQuestionsAdapter roundQuestionsAdapter, QuestionsList questionsList, String answer1){
        this.roundQuestionsAdapter=roundQuestionsAdapter;
        this.questionsList=questionsList;
    }

    public QuestionsList getQuestionsList() {
        return questionsList;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    @Override
    public void listParsed(List<Question> list)
    {
        answer1=list.get(0).getThisAnswer();
        //questionsList.setQuestionsList((List<Question>) list);
        roundQuestionsAdapter.addAll(list);
    }
}
