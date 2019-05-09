package com.example.paperlessquiz.question;

import java.io.Serializable;
import java.util.List;

public class QuestionsList implements Serializable {
    public static final String INTENT_PUT_EXTRA_NAME_THIS_ROUND_ANSWERS = "thisRoundAnswers";
    private List<Question> questionsList;

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }
}
