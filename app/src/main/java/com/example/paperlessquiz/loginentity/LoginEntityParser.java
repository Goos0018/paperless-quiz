package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginEntityParser implements JsonParser<LoginEntity> {

    //Strings here MUST match the headers in the Organizers and Teams tabs of the Quiz sheet
    public static final String ID = "ID";
    public static final String TYPE = "Type";
    public static final String NAME = "Name";
    public static final String PASSKEY = "Passkey";
    public static final String PRESENT = "Present";
    public static final String LOGGED_IN = "LoggedIn";
    public static final int START_OF_ROUNDS = 6;

    public LoginEntity parse(JSONObject jo) throws JSONException {
        LoginEntity loginEntity = new LoginEntity(jo.getInt(ID), jo.getString(TYPE),
                jo.getString(NAME), jo.getString(PASSKEY));
        if (jo.getString(TYPE).equals(LoginEntity.SELECTION_PARTICIPANT)) {
            // If this is a participant, fill out the rest of the info
            loginEntity.setPresent(jo.getBoolean(PRESENT));
            loginEntity.setLoggedIn(jo.getBoolean(LOGGED_IN));
            ArrayList<Boolean> answersSubmitted = new ArrayList<>();
            for (int i = START_OF_ROUNDS; i < jo.length(); i++) {
                answersSubmitted.add(i - START_OF_ROUNDS, Boolean.valueOf(jo.getBoolean("" + (i - START_OF_ROUNDS+1))));
            }
            loginEntity.setAnswerForRndsSubmitted(answersSubmitted);
        }
        return loginEntity;
    }
}
