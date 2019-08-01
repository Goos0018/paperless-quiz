package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.Question;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

//TODO: foresee for correctAnswer not to be present!
public class QuestionParser implements JsonParser<Question> {

    @Override
    public Question parse(JSONObject jo) throws JSONException {
        int roundNr, idQuestion,questionNr,questionScore,questionType;
        String questionName,questionHint,questionFull,questionAnswer;
        Question question;
        try {
            roundNr = jo.getInt(QuizDatabase.COLNAME_ROUND_NR);
            idQuestion = jo.getInt(QuizDatabase.COLNAME_ID_QUESTION);
            questionNr = jo.getInt(QuizDatabase.COLNAME_QUESTION_NR);
            questionScore = jo.getInt(QuizDatabase.COLNAME_QUESTION_SCORE);
            questionType = jo.getInt(QuizDatabase.COLNAME_QUESTION_TYPE);
            questionName = jo.getString(QuizDatabase.COLNAME_QUESTION_NAME);
            questionHint = jo.getString(QuizDatabase.COLNAME_QUESTION_HINT);
            questionFull = jo.getString(QuizDatabase.COLNAME_QUESTION_FULL);
            try{
            questionAnswer = jo.getString(QuizDatabase.COLNAME_QUESTION_ANSWER);}
            catch (Exception e){
                //20190728: if its not the corrector, its normal that we can't get the correct answers
                questionAnswer = "";
            }
        } catch (Exception e) {
            roundNr = 0;
            idQuestion = 0;
            questionNr = 0;
            questionScore = 0;
            questionType = 0;
            questionName = "Error parsing " + jo.toString();
            questionHint = "";
            questionFull = "";
            questionAnswer = "";
            }
        question = new Question(roundNr, idQuestion, questionNr, questionType, questionName, questionHint, questionFull, questionAnswer, questionScore);
        return question;
    }
}

