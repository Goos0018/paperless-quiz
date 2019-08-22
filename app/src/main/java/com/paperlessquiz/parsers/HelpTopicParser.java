package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.HelpTopic;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class HelpTopicParser implements JsonParser<HelpTopic> {

    @Override
    public HelpTopic parse(JSONObject jo) throws JSONException {
        int idHelpTopic, idUser, orderNr, type;
        String remark, response;
        HelpTopic topic;
        try {
            idHelpTopic = jo.getInt(QuizDatabase.COLNAME_HELPTOPICID);
            idUser = jo.getInt(QuizDatabase.COLNAME_ID_USER);
            orderNr = jo.getInt(QuizDatabase.COLNAME_HELPTOPICORDERNR);
            type = jo.getInt(QuizDatabase.COLNAME_HELPTOPICTYPE);
            remark = jo.getString(QuizDatabase.COLNAME_HELPTOPICREMARK);
            try {
                response = jo.getString(QuizDatabase.COLNAME_HELPTOPICRESPONSE);
            } catch (Exception e) {
                response = "";
            }
        } catch (Exception e) {
            idHelpTopic = 0;
            idUser = 0;
            orderNr = 0;
            type = 0;
            remark = "Error parsing " + jo.toString();
            response="";
        }
        topic = new HelpTopic(idHelpTopic, orderNr, type, idUser, remark, response);
        return topic;
    }
}
