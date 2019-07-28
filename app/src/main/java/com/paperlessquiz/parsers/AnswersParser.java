package com.paperlessquiz.parsers;

import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswersParser implements JsonParser<Answer> {

    @Override
    public Answer parse(JSONObject jo) throws JSONException {
        boolean isCorrect, isCorrected;
        int teamNr, roundNr,questionNr;
        String theAnswer;
        Answer answer;
        try {
            isCorrect = (jo.getInt(QuizDatabase.COLNAME_ANSWER_CORRECT) == 0);
            isCorrected = (jo.getInt(QuizDatabase.COLNAME_ANSWER_CORRECTED) == 0);
            teamNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            theAnswer = jo.getString(QuizDatabase.COLNAME_ANSWER);
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            questionNr = jo.getInt(QuizDatabase.COLNAME_QUESTION_NR);
        } catch (Exception e) {
            isCorrect = false;
            isCorrected = false;
            teamNr =0;
            theAnswer = "Error parsing " + jo.toString();
            roundNr=0;
            questionNr=0;
        }
        answer = new Answer(teamNr, roundNr,questionNr, theAnswer, isCorrected, isCorrect);
        return answer;
    }
}
