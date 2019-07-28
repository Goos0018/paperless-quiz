package com.paperlessquiz.parsers;

import com.paperlessquiz.logmessage.LogMessage;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class LogMessageParser implements JsonParser {

    @Override
    public Object parse(JSONObject jo) throws JSONException {
        int idTeam, level;
        String dateTime;
        String message;
        LogMessage logMessage;
        try {
            idTeam = jo.getInt(QuizDatabase.COLNAME_ID_USER);
            level = jo.getInt(QuizDatabase.COLNAME_LEVEL);
            dateTime = jo.getString(QuizDatabase.COLNAME_DATE_TIME);
            message = jo.getString(QuizDatabase.COLNAME_MESSAGE);
        } catch (Exception e) {
            idTeam = 0;
            level=0;
            dateTime="";
            message = "Error parsing " + jo.toString();
        }
        logMessage = new LogMessage(idTeam,level,dateTime,message);
        return logMessage;
    }
}
