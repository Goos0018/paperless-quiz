package com.example.paperlessquiz.quizextras;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizExtrasParser implements JsonParser<QuizExtras>
{
    //Strings here MUST match the headers in the QuizData tab of the Quiz sheet
    public static final String QUIZ_OPEN = "QuizOpen";
    public static final String QUIZ_NR_OF_ROUNDS = "QuizNrOfRounds";
    public static final String QUIZ_NR_OF_PARTICIPANTS = "QuizNrOfParticipants";
    public static final String QUIZ_EXTRAS_SHEETNAME = "QuizData";
    public static final String INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS = "thisQuizExtras";

    public QuizExtras parse(JSONObject jo) throws JSONException {
        return new QuizExtras(jo.getBoolean(QUIZ_OPEN),
                jo.getInt(QUIZ_NR_OF_ROUNDS),jo.getInt(QUIZ_NR_OF_PARTICIPANTS));
    }

}
