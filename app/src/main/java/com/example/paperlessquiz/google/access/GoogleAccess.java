package com.example.paperlessquiz.google.access;

public class GoogleAccess {

    static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?";
    public static final String PARAMNAME_DOC_ID = "DocID=";
    public static final String PARAMNAME_SHEET = "Sheet=";
    public static final String PARAMNAME_ACTION = "action=";
    public static final String PARAM_CONCATENATOR = "&";
    public static final String PARAMNAME_LINE_TO_ADD = "LineToAdd";
    public static final String PARAMNAME_USERID = "userID=";
    public static final String PARAMNAME_ROUNDID = "roundID=";
    public static final String PARAMNAME_ANSWERS = "answers=";
    public static final String PARAMNAME_TEAMID = "teamID=";
    public static final String PARAMNAME_FIRSTQUESTION = "FirstQuestion=";

    public static final String PARAMVALUE_GETDATA = "getdata";
    public static final String PARAMVALUE_SUBMITANSWERS = "submitAnswers";
    public static final String PARAMVALUE_ADDLINE = "addline";

    public static final String SHEET_QUIZLIST = "QuizList";
    public static final String SHEET_QUIZDATA = "QuizData";
    public static final String SHEET_ROUNDS = "Rounds";
    public static final String SHEET_QUESTIONS = "Questions";
    public static final String SHEET_TEAMS = "Teams";
    public static final String SHEET_ORGANIZERS = "Organizers";
    public static final String SHEET_TEAMCONTROL = "TeamRegistration";
    public static final String SHEET_ANSWERS = "Answers";
}
