package com.example.paperlessquiz.round;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class RoundParser implements JsonParser<Round> {

    //Strings here MUST match the headers in the Rounds tab of the Quiz sheet
    public static final String ROUND_NR = "RoundNr";
    public static final String ROUND_NAME = "RoundName";
    public static final String ROUND_DESCRIPTION = "RoundDescription";
    public static final String ROUND_NR_OF_QUESTIONS = "RoundNrOfQuestions";
    public static final String ROUND_ACCEPTS_ANSWERS = "RoundAcceptsAnswers";
    public static final String ROUND_ACCEPTS_CORRECTIONS = "RoundAcceptsCorrections";
    public static final String ROUND_IS_CORRECTED = "RoundIsCorrected";


    @Override
    public Round parse(JSONObject jo) throws JSONException {
        Round round;
        try {
            round = new Round(jo.getInt(ROUND_NR), jo.getString(ROUND_NAME),
                    jo.getString(ROUND_DESCRIPTION), jo.getInt(ROUND_NR_OF_QUESTIONS),
                    jo.getBoolean(ROUND_ACCEPTS_ANSWERS), jo.getBoolean(ROUND_ACCEPTS_CORRECTIONS),
                    jo.getBoolean(ROUND_IS_CORRECTED));
        } catch (Exception e){
            round = new Round(jo.getInt(ROUND_NR), jo.getString(ROUND_NAME),
                    jo.getString(ROUND_DESCRIPTION), jo.getInt(ROUND_NR_OF_QUESTIONS),
                    false, false,false);
        }
        return round;
    }
}
