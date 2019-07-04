package com.paperlessquiz.quiz;

import android.content.Context;

import com.paperlessquiz.googleaccess.GoogleAccess;

public class QuizSubmitter {
    private Context context;
    private String sheetDocID;
    private Quiz quiz;

    public String generateParams(String sheet) {
        return GoogleAccess.PARAMNAME_DOC_ID + sheetDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    }



}
