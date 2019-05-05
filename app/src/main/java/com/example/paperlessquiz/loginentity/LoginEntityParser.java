package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginEntityParser implements JsonParser<LoginEntity> {
    public LoginEntity parse(JSONObject jo) throws JSONException {
        //Strings here MUST match the headers in the QuizList tab of the Quizlist sheet
        return new LoginEntity(jo.getString("ID"), jo.getString("Type"),
                jo.getString("Name"), jo.getString("Passkey"));
    }


}
