package com.example.paperlessquiz.google.access;

import android.content.Context;

import com.example.paperlessquiz.MyApplication;

import java.util.Date;

public class EventLogger {

    public static final int LEVEL_DEBUG = 5;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARNING = 1;
    public static final int LEVEL_ERROR = 0;
    public static final int LEVEL_ALWAYS = -1;

    private Context context;
    private String docID;
    private String sheet;

    public EventLogger(Context context, String docID, String sheet) {
        this.context = context;
        this.docID = docID;
        this.sheet = sheet;
    }

    public void logEvent(String team, int level, String message) {
        try {
            if (MyApplication.theQuiz.getListData().getAppDebugLevel() < level) {
            } else {
                String strToday = new Date().toString();
                String strLevel;
                switch (level) {
                    case LEVEL_ALWAYS:
                        strLevel = "ALWAYS";
                        break;
                    case LEVEL_INFO:
                        strLevel = "INFO";
                        break;
                    case LEVEL_ERROR:
                        strLevel = "ERROR";
                        break;
                    case LEVEL_WARNING:
                        strLevel = "WARNING";
                        break;
                    case LEVEL_DEBUG:
                        strLevel = "DEBUG";
                        break;
                    default:
                        strLevel = "DEFAULT";
                }
                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + docID + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_LINE_TO_ADD + "[\"" + strToday + "\",\"" + team + "\",\"" + strLevel + "\",\"" + message + "\"]" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_ADDLINE;
                GoogleAccessSet logger = new GoogleAccessSet(context, scriptParams);
                logger.setData(new LoadingListenerSilent());
            }
        } catch (Exception e) {
        }
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}
