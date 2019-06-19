package com.example.paperlessquiz.corrections;

import com.example.paperlessquiz.google.access.JsonParser;
import com.example.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CorrectionsListParser implements JsonParser<CorrectionsList> {
    //Headers are taken from the QuizGenerator class and must match those of course

    @Override
    public CorrectionsList parse(JSONObject jo) throws JSONException {
        CorrectionsList correctionsList = new CorrectionsList(jo.getString(QuizGenerator.QUESTION_ID), jo.getInt(QuizGenerator.ROUND_NR), jo.getInt(QuizGenerator.QUESTION_NR));
        ArrayList<Correction> allCorrections = new ArrayList<>();
        boolean isCorrect, isCorrected;
        for (int i = 3; i < jo.length(); i++) {
            //Check if there is something filled out, if not, the question has not been corrected
            try {
                isCorrect = jo.getBoolean(QuizGenerator.TEAMS_PREFIX + (i - QuizGenerator.ANSWERSSHEET_START_OF_TEAMS + 1));
                isCorrected = true;
            } catch (Exception e) {
                isCorrect = false;
                isCorrected = false;
            }
            //String test = jo.getString("" + (i - START_OF_TEAMS + 1));
            allCorrections.add(i - QuizGenerator.ANSWERSSHEET_START_OF_TEAMS, new Correction(isCorrect, isCorrected));
        }
        correctionsList.setAllCorrections(allCorrections);
        return correctionsList;
    }
}
