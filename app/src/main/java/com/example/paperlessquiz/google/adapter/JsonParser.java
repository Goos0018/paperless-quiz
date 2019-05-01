package com.example.paperlessquiz.google.adapter;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonParser<T> {
    T parse(JSONObject jo) throws JSONException;
}
