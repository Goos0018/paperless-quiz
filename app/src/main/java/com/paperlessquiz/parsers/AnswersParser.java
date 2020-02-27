package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.Answer;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class AnswersParser implements JsonParser<Answer> {

    @Override
    public Answer parse(JSONObject jo) throws JSONException {
        boolean isCorrect, isCorrected, isSubmitted;
        int teamNr, roundNr,questionNr;
        String theAnswer;
        Answer answer;
        try {
            isCorrect = (jo.getInt(QuizDatabase.COLNAME_ANSWER_CORRECT) == 1);
            isCorrected = (jo.getInt(QuizDatabase.COLNAME_ANSWER_CORRECTED) == 1);
            teamNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            theAnswer = jo.getString(QuizDatabase.COLNAME_ANSWER);
            isSubmitted = !(theAnswer.equals(""));
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            questionNr = jo.getInt(QuizDatabase.COLNAME_QUESTION_NR);
        } catch (Exception e) {
            isCorrect = false;
            isCorrected = false;
            teamNr =0;
            theAnswer = "Error parsing " + jo.toString();
            roundNr=0;
            questionNr=0;
            isSubmitted=false;
        }
        answer = new Answer(teamNr, roundNr,questionNr, theAnswer, isCorrected, isCorrect,isSubmitted);
        return answer;
    }
}
