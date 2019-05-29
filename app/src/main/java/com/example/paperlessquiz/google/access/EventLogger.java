package com.example.paperlessquiz.google.access;

import android.content.Context;

import java.util.Date;

public class EventLogger {

    private Context context;
    private String docID;
    private String sheet;

    public EventLogger(Context context, String docID, String sheet) {
        this.context = context;
        this.docID = docID;
        this.sheet = sheet;
    }

    public void logEvent(String team, String message){
        String strToday = new Date().toString();
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + docID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_LINE_TO_ADD + "[\"" + strToday + "\",\"" + team + "\",\"" + message+ "\"]" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_ADDLINE;
        GoogleAccessSet logger = new GoogleAccessSet(context, scriptParams);
        logger.setData(new LoadingListenerSilent());
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
