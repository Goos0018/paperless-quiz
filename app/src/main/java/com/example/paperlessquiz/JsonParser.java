package com.example.paperlessquiz;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonParser<T> {

    protected abstract T parse(JSONObject jo) throws JSONException;
}
