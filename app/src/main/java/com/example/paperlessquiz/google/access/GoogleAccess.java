package com.example.paperlessquiz.google.access;

public class GoogleAccess {
    //This is the base URL & parameters for the Google script to interact with any google sheet
    //static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbxF4oneivWH9QUnOyEJRWWDNIfdaSft5idzfNWgz7USI0ZzKw_o/exec?"; => RME Version
    //static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbzBnefs99HeSwpCQEHY4c6K4pEvpWoiuE3dEU-UuKQ-yRtO-Awe/exec?"; => PQ version
    static final String SCRIPT_URL = "https://script.google.com/macros/s/AKfycbz1qygz4GdX4kDFYk1NrPT0zRwzveAUAUXfByjW0EhMliLhB-c/exec?"; //=> new PQ version
    public static final String PARAM_CONCATENATOR = "&";
    //Paramater names
    public static final String PARAMNAME_DEBUGLEVEL = "debuglevel=";
    public static final String PARAMNAME_DOC_ID = "docID=";
    public static final String PARAMNAME_SHEET = "sheet=";
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
    public static final String PARAMVALUE_FIRST_TEAM_NR = "1";

    public static final String PARAMNAME_RECORD_ID = "RecordID=";
}
