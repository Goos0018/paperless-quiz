package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginEntityParser implements JsonParser<LoginEntity> {

    //Strings here MUST match the headers in the Organizers and Teams tabs of the Quiz sheet
    public static final String ID = "ID";
    public static final String TYPE = "Type";
    public static final String NAME = "Name";
    public static final String PASSKEY = "Passkey";

    public LoginEntity parse(JSONObject jo) throws JSONException {
        return new LoginEntity(jo.getInt(ID), jo.getString(TYPE),
                jo.getString(NAME), jo.getString(PASSKEY));
    }
}
