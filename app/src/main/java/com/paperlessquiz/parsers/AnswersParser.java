package com.paperlessquiz.parsers;

import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;

public class AnswersParser implements JsonParser<Answer> {

    @Override
    public Answer parse(JSONObject jo) throws JSONException {
        boolean isCorrect, isCorrected;
        int idTeam, idQuestion, idAnswer;
        String theAnswer;
        Answer answer;
        try {
            isCorrect = (jo.getInt(QuizDatabase.COLNAME_ANSWER_CORRECT) == 0);
            isCorrected = (jo.getInt(QuizDatabase.COLNAME_ANSWER_CORRECTED) == 0);
            idTeam = jo.getInt(QuizDatabase.COLNAME_ID_TEAM);
            idQuestion = jo.getInt(QuizDatabase.COLNAME_ID_QUESTION);
            idAnswer = jo.getInt(QuizDatabase.COLNAME_ID_ANSWER);
            theAnswer = jo.getString(QuizDatabase.COLNAME_ANSWER);
        } catch (Exception e) {
            isCorrect = false;
            isCorrected = false;
            idTeam=0;
            idQuestion=0;
            idAnswer=0;
            theAnswer = "Error parsing " + jo.toString();
        }
        answer = new Answer(idTeam, idQuestion, idAnswer, theAnswer, isCorrected, isCorrect);
        return answer;
    }
}
