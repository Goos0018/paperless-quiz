package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.JsonParser;
import com.example.paperlessquiz.quiz.QuizGenerator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginEntityParser implements JsonParser<LoginEntity> {
    //Headers are taken from the QuizGenerator class and must match those of course

    public LoginEntity parse(JSONObject jo) throws JSONException {
        LoginEntity loginEntity = new LoginEntity(jo.getInt(QuizGenerator.LOGIN_ENTITY_ID), jo.getString(QuizGenerator.LOGIN_ENTITY_TYPE),
                jo.getString(QuizGenerator.LOGIN_ENTITY_NAME), jo.getString(QuizGenerator.LOGIN_ENTITY_PASSKEY));
        if (jo.getString(QuizGenerator.LOGIN_ENTITY_TYPE).equals(LoginEntity.SELECTION_PARTICIPANT)) {
            // If this is a participant, fill out the rest of the info
            try {
                loginEntity.setPresent(jo.getBoolean(QuizGenerator.TEAM_PRESENT));
                loginEntity.setLoggedIn(jo.getBoolean(QuizGenerator.TEAM_LOGGED_IN));
            }
            catch (Exception e){
                loginEntity.setPresent(false);
                loginEntity.setLoggedIn(false);
            }
            ArrayList<Boolean> answersSubmitted = new ArrayList<>();
            boolean valueToSet;
            for (int i = QuizGenerator.TEAMSHEET_START_OF_ROUNDS; i < jo.length(); i++) {
               try {
                   valueToSet = jo.getBoolean("" + (i - QuizGenerator.TEAMSHEET_START_OF_ROUNDS +1));
               }
               catch (Exception e){
                   //if we cannot make sense of what we get, return false
                   valueToSet = false;
                }
                answersSubmitted.add(i - QuizGenerator.TEAMSHEET_START_OF_ROUNDS, Boolean.valueOf(valueToSet));
            }
            loginEntity.setAnswerForRndsSubmitted(answersSubmitted);
        }
        return loginEntity;
    }
}
