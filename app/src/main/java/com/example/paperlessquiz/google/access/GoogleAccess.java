package com.example.paperlessquiz.google.access;

public class GoogleAccess {
    //This is where we get the list of quiz'es from
    public static final String QUIZLIST_DOC_ID = "1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk";
    public static final String QUIZLIST_TABNAME = "QuizList";
    //This is the base URL & parameters for the Google script to interact with any google sheet
    static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?";
    public static final String PARAM_CONCATENATOR = "&";
    //Paramater names
    public static final String PARAMNAME_DEBUGLEVEL = "debuglevel=";
    public static final String PARAMNAME_DOC_ID = "DocID=";
    public static final String PARAMNAME_SHEET = "Sheet=";
    public static final String PARAMNAME_ACTION = "action=";
    public static final String PARAMNAME_LINE_TO_ADD = "LineToAdd=";
    public static final String PARAMNAME_NEWVALUES = "NewValues=";
    public static final String PARAMNAME_RECORDID = "RecordID=";
    public static final String PARAMNAME_FIELDNAME = "Fieldname=";
    public static final String PARAMNAME_USERID = "userID=";
    public static final String PARAMNAME_ROUNDID = "roundID=";
    public static final String PARAMNAME_ANSWERS = "answers=";
    public static final String PARAMNAME_TEAMID = "teamID=";
    public static final String PARAMNAME_FIRSTQUESTION = "FirstQuestion=";
    //Possible parameter values
    public static final String PARAMVALUE_GETDATA = "getdata";
    public static final String PARAMVALUE_SETDATA = "setdata";
    public static final String PARAMVALUE_SUBMITANSWERS = "submitAnswers";
    public static final String PARAMVALUE_ADDLINE = "addline";
    public static final String PARAMVALUE_DEBUGLEVEL = "0";
    //Tabs in the Quiz sheet
    //public static final String SHEET_QUIZLIST = "QuizList";
    public static final String SHEET_QUIZDATA = "QuizData";
    public static final String SHEET_ROUNDS = "Rounds";
    public static final String SHEET_QUESTIONS = "Questions";
    public static final String SHEET_TEAMS = "Teams";
    public static final String SHEET_ORGANIZERS = "Organizers";
    //public static final String SHEET_TEAMCONTROL = "TeamRegistration";
    public static final String SHEET_ANSWERS = "Answers";
    public static final String SHEET_SCORES = "Scores";
    public static final String SHEET_EVENTLOG = "EventLog";
}
