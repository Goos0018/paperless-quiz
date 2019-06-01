package com.example.paperlessquiz.answerslist;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnswersListParser implements JsonParser<AnswersList> {
    //Strings here MUST match the headers in the Answers tab of the Quiz sheet
    public static final String QUESTION_ID = "QuestionID";
    public static final String ROUND_NR = "RoundNr";
    public static final String QUESTION_NR = "QuestionNr";
    //The other headers in the Answers tab are assumed to be team numbers

    @Override
    public AnswersList parse(JSONObject jo) throws JSONException {
        AnswersList answersList = new AnswersList(jo.getString(QUESTION_ID),jo.getInt(ROUND_NR), jo.getInt(QUESTION_NR));
        ArrayList<Answer> allAnswers = new ArrayList<>();
        for (int i = 3; i < jo.length(); i++) {
            allAnswers.add(i-3,new Answer(jo.getString("" + (i-2))));
        }
        answersList.setAllAnswers(allAnswers);
        return answersList;
    }
}
