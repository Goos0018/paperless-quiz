package com.paperlessquiz.quiz;

public class QuizDatabase {

    // Constants needed to retrieve data from the SQL db
    public static final String PHP_URL = "http://paperlessquiz.be/php/";
    public static final String PHP_STARTPARAM = "?";
    public static final String PHP_PARAMCONCATENATOR = "&";
    //Parameter names - must match those defined in constants.php
    public static final String PARAMNAME_IDQUIZ = "idQuiz=";
    public static final String PARAMNAME_IDUSER = "idUser=";
    public static final String PARAMNAME_IDQUESTION = "idQuestion=";
    public static final String PARAMNAME_IDROUND = "idRound=";
    public static final String PARAMNAME_USERPASSWORD = "userPassword=";
    public static final String PARAMNAME_QUERY = "query=";
    public static final String PARAMNAME_THEANSWER = "answer=";
    public static final String PARAMNAME_THEIDFORTHEQUERY = "theId=";
    public static final String PARAMNAME_NEWROUNDSTATUS = "newRoundStatus=";
    public static final String PARAMNAME_USERTOUPDATE = "userToUpdate=";
    public static final String PARAMNAME_NEWUSERSTATUS = "newUserStatus=";
    public static final String PARAMNAME_NEWUSERNAME = "newUserName=";
    public static final String PARAMNAME_ISCORRECT = "isCorrect=";
    public static final String PARAMNAME_IDTEAMS = "idTeams=";
    // Available PHP scripts
    //public static final String GETALLDATA_SCRIPT = "getalldata.php";
    public static final String SCRIPT_GET_QUIZLIST = "getquizlist.php";
    public static final String SCRIPT_GET_QUIZUSERS = "getuserlist.php";
    public static final String SCRIPT_GET_QUIZDATA = "getquizdata.php";
    //public static final String SCRIPT_GET_ALLANSWERS = "getallanswers.php";
    public static final String SCRIPT_AUTHENTICATE = "authenticate.php";
    public static final String SCRIPT_SUBMITANSWER = "submitanswer.php";
    public static final String SCRIPT_SETANSWERSSUBMITTED = "setanswerssubmitted.php";
    public static final String SCRIPT_UPDATEROUNDSTATUS = "updateroundstatus.php";
    public static final String SCRIPT_UPDATEMYSTATUS = "updatemystatus.php";
    public static final String SCRIPT_UPDATETEAM = "updateteam.php";
    public static final String SCRIPT_CORRECTQUESTION = "correctquestion.php";

    //Param values - query names
    public static final String PARAMVALUE_QRY_ALL_ROUNDS = "rounds";
    public static final String PARAMVALUE_QRY_ALL_QUESTIONS = "questions";
    public static final String PARAMVALUE_QRY_MY_ANSWERS = "myanswers";
    public static final String PARAMVALUE_QRY_ALL_ANSWERS = "allanswers";
    public static final String PARAMVALUE_QRY_FULL_QUESTIONS = "fullquestions";
    public static final String PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED = "answerssubmitted";
    public static final String PARAMVALUE_QRY_MY_EVENTLOGS = "myevents";


    public static final String PARAMVALUE_QRY_ALL_TEAMS_FOR_QUIZ = "qry_all-teams-for-quiz";
    //Request ID's
    //GET Requests
    public static final int REQUEST_ID_GET_QUIZLIST = 0;
    public static final int REQUEST_ID_GET_USERS = 1;
    public static final int REQUEST_ID_GET_ROUNDS = 2;
    public static final int REQUEST_ID_GET_QUESTIONS = 3;
    public static final int REQUEST_ID_GET_ANSWERS = 4;
    public static final int REQUEST_ID_GET_ANSWERSSUBMITTED = 5;
    public static final int REQUEST_ID_GET_EVENTLOGS = 6;
    public static final int REQUEST_ID_GET_ALLANSWERS = 7;
    public static final int REQUEST_ID_GET_FULLQUESTIONS = 8;
    //Submit requests
    public static final int REQUEST_ID_AUTHENTICATE = 100;
    public static final int REQUEST_ID_SUBMITANSWER = 101;
    public static final int REQUEST_ID_SETANSWERSSUBMITTED = 102;
    public static final int REQUEST_ID_UPDATEROUNDSTATUS = 103;
    public static final int REQUEST_ID_UPDATEMYSTATUS = 104;
    public static final int REQUEST_ID_UPDATETEAM = 105;
    public static final int REQUEST_ID_CORRECTQUESTION = 106;

    //User types - these must match what is defined in the constants.php
    public static final int USERTYPE_TEAM = 0;
    public static final int USERTYPE_QUIZMASTER = 1;
    public static final int USERTYPE_RECEPTIONIST = 2;
    public static final int USERTYPE_CORRECTOR = 3;
    public static final int USERTYPE_JUROR = 4;

    //User Statuses  - these must match what is defined in the constants.php
    public static final int USERSTATUS_NOTPRESENT = 0;
    public static final int USERSTATUS_PRESENTNOTLOGGEDIN = 1;
    public static final int USERSTATUS_PRESENTLOGGEDIN = 2;

    //Round Statuses
    public static final int ROUNDSTATUS_CLOSED = 0;
    public static final int ROUNDSTATUS_OPENFORANSWERS = 1;
    public static final int ROUNDSTATUS_OPENFORCORRECTIONS = 2;
    public static final int ROUNDSTATUS_CORRECTED = 3;


    //Column names in the SQL database - used to parse the JSON objects
    //Column names table Answers
    public static final String COLNAME_ID_ANSWER = "idAnswer";
    public static final String COLNAME_ANSWER_CORRECT = "AnswerCorrect";
    public static final String COLNAME_ANSWER_CORRECTED = "AnswerCorrected";
    public static final String COLNAME_ANSWER = "Answer";
    //Column names table AnswersSubmitted
    public static final String COLNAME_SUBMITTED = "Submitted";
    //Column names table EventLog
    public static final String COLNAME_LEVEL = "Level";
    public static final String COLNAME_DATE_TIME = "DateTime";
    public static final String COLNAME_MESSAGE = "Message";
    //Column names table Questions
    public static final String COLNAME_ID_QUESTION = "idQuestion";
    public static final String COLNAME_QUESTION_NR = "QuestionNr";
    public static final String COLNAME_QUESTION_SCORE = "QuestionScore";
    public static final String COLNAME_QUESTION_TYPE = "QuestionType";
    public static final String COLNAME_QUESTION_NAME = "QuestionName";
    public static final String COLNAME_QUESTION_HINT = "QuestionHint";
    public static final String COLNAME_QUESTION_FULL = "QuestionFull";
    public static final String COLNAME_QUESTION_ANSWER = "QuestionAnswer";
    //Column names table QuizList
    public static final String COLNAME_ID_QUIZ = "idQuiz";
    public static final String COLNAME_QUIZ_NAME = "QuizName";
    public static final String COLNAME_QUIZ_DESCRIPTION = "QuizDescription";
    public static final String COLNAME_QUIZ_LOGO_URL = "QuizLogoURL";
    public static final String COLNAME_QUIZ_PDFURL = "QuizPDFURL";
    public static final String COLNAME_QUIZ_DEBUG_LEVEL = "QuizDebugLevel";
    public static final String COLNAME_QUIZ_SHEET_DOC_ID = "QuizSheetDocID";
    //Column names table Rounds
    public static final String COLNAME_ID_ROUND = "idRound";
    public static final String COLNAME_ROUND_NR = "RoundNr";
    public static final String COLNAME_ROUND_STATUS = "RoundStatus";
    public static final String COLNAME_ROUND_DESCRIPTION = "RoundDescription";
    public static final String COLNAME_ROUND_NAME = "RoundName";
    //Column names table Users
    public static final String COLNAME_ID_USER = "idUser";
    public static final String COLNAME_USER_NR = "UserNr";
    public static final String COLNAME_USER_STATUS = "UserStatus";
    public static final String COLNAME_USER_TYPE = "UserType";
    //public static final String COLNAME_TEAM_PASSWORD = "TeamPassword";
    public static final String COLNAME_USER_NAME = "UserName";

    //Various other constants
    public static final String INTENT_EXTRANAME_IS_ORGANIZER = "isOrganizer";
}
