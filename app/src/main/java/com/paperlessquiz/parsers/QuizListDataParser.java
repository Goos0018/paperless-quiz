package com.paperlessquiz.parsers;

import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizListData;

import org.json.JSONException;
import org.json.JSONObject;

public class QuizListDataParser implements JsonParser<QuizListData> {

    @Override
    public QuizListData parse(JSONObject jo) throws JSONException {
        int idQuiz, debugLevel;
        String name, description, logoURL, quizPdfURL, sheetDocID;
        QuizListData quizListData;
        try {
            idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            name = jo.getString(QuizDatabase.COLNAME_QUIZ_NAME);
            description = jo.getString(QuizDatabase.COLNAME_QUIZ_DESCRIPTION);
            logoURL = jo.getString(QuizDatabase.COLNAME_QUIZ_LOGO_URL);
            quizPdfURL = jo.getString(QuizDatabase.COLNAME_QUIZ_PDFURL);
            //debugLevel = jo.getInt(QuizDatabase.COLNAME_QUIZ_DEBUG_LEVEL);
            //sheetDocID = jo.getString(QuizDatabase.COLNAME_QUIZ_SHEET_DOC_ID);
        } catch (Exception e) {
            idQuiz = 0;
            name = "Error parsing " + jo.toString();;
            description = "";
            logoURL = "";
            quizPdfURL = "";
            //debugLevel = 0;
            //sheetDocID = "";
        }
        quizListData = new QuizListData(idQuiz, name, description, logoURL, quizPdfURL);
        return quizListData;
    }
}
