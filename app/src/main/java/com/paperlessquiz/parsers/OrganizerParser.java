package com.paperlessquiz.parsers;

import com.paperlessquiz.loginentity.Organizer;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class OrganizerParser implements JsonParser<Organizer> {

    @Override
    public Organizer parse(JSONObject jo) throws JSONException {
        int idQuiz, idOrganizer, organizerType, organizerNr;
        String organizerName, organizerPassword;
        Organizer organizer;
        try {
            idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            idOrganizer = jo.getInt(QuizDatabase.COLNAME_ID_ORGANIZER);
            organizerType = jo.getInt(QuizDatabase.COLNAME_ORGANIZER_TYPE);
            organizerNr = jo.getInt(QuizDatabase.COLNAME_ORGANIZER_NR);
            organizerPassword = jo.getString(QuizDatabase.COLNAME_ORGANIZER_PASSWORD);
            organizerName = jo.getString(QuizDatabase.COLNAME_ORGANIZER_NAME);
        } catch (Exception e) {
            idQuiz = 0;
            idOrganizer = 0;
            organizerType=0;
            organizerNr=0;
            organizerPassword="";
            organizerName = "Error parsing " + jo.toString();
        }
        organizer = new Organizer(idQuiz, idOrganizer,organizerName,organizerPassword,organizerType,organizerNr);
        return organizer;
    }
}
