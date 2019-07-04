package com.paperlessquiz.question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionsList extends ArrayList<Question> implements Serializable {
    public static final String INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS = "thisRoundAnswers";
    private ArrayList<Question> questionsList;

    public QuestionsList() {
        this.questionsList = new ArrayList<Question>();
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    /*public void addQuestion(Question q)
    {
        questionsList.add(q);
    }
    public void addQuestion(int i,Question q)
    {
        questionsList.add(i,q);
    }*/
}
