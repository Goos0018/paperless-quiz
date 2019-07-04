package com.paperlessquiz.answer;

import com.paperlessquiz.googleaccess.JsonParser;
import com.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnswersListParser implements JsonParser<AnswersList> {
    //Headers are taken from the QuizGenerator class and must match those of course

    @Override
    public AnswersList parse(JSONObject jo) throws JSONException {
        AnswersList answersList = new AnswersList(jo.getString(QuizGenerator.QUESTION_ID),jo.getInt(QuizGenerator.ROUND_NR), jo.getInt(QuizGenerator.QUESTION_NR));
        ArrayList<Answer> allAnswers = new ArrayList<>();
        for (int i = 3; i < jo.length(); i++) {
            allAnswers.add(i- QuizGenerator.ANSWERSSHEET_START_OF_TEAMS,new Answer(jo.getString(QuizGenerator.TEAMS_PREFIX + (i- QuizGenerator.ANSWERSSHEET_START_OF_TEAMS +1))));
        }
        answersList.setAllAnswers(allAnswers);
        return answersList;
    }
}
