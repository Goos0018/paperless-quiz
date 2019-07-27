package com.paperlessquiz.quiz;

public class QuizDatabase {

    // Constants needed to retrieve data from the SQL db
    public static final String PHP_URL = "http://paperlessquiz.be/php/";
    public static final String PHP_STARTPARAM = "?";
    public static final String PHP_PARAMCONCATENATOR = "&";
    public static final String PARAMNAME_TABLE = "table=";
    public static final String PARAMNAME_ID = "id=";
    public static final String PARAMNAME_QUERY = "query=";
    // Available PHP scripts
    //public static final String GETALLDATA_SCRIPT = "getalldata.php";
    public static final String GETDATA_SCRIPT = "getquizdata.php";
    public static final String GETQUIZLIST_SCRIPT = "getquizlist.php";
    //Param values - table names
    public static final String PARAMVALUE_TBL_ANSWERS = "Answers";
    public static final String PARAMVALUE_TBL_ANSWERSSUBMITTED = "AnswersSubmitted";
    public static final String PARAMVALUE_TBL_EVENTLOG = "EventLog";
    public static final String PARAMVALUE_TBL_ORGANIZERS = "Organizers";
    public static final String PARAMVALUE_TBL_QUESTIONS = "Questions";
    public static final String PARAMVALUE_TBL_QUIZLIST = "QuizList";
    public static final String PARAMVALUE_TBL_ROUNDS = "Rounds";
    public static final String PARAMVALUE_TBL_TEAMS = "Teams";
    //Param values - query names
    public static final String PARAMVALUE_QRY_ALL_ANSWERS_FOR_QUIZ = "qry_all-answers-for-quiz";
    public static final String PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED_FOR_QUIZ = "qry_all-answerssubmitted-for-quiz";
    public static final String PARAMVALUE_QRY_ALL_EVENTLOGS_FOR_QUIZ = "qry_all-eventlogs-for-quiz";
    public static final String PARAMVALUE_QRY_ALL_EVENTLOGS_FOR_TEAM = "qry_all-eventlogs-for-team";
    public static final String PARAMVALUE_QRY_ALL_ORGANIZERS_FOR_QUIZ = "qry_all-organizers-for-quiz";
    public static final String PARAMVALUE_QRY_ALL_QUESTIONS_FOR_QUIZ = "qry_all-questions-for-quiz";
    public static final String PARAMVALUE_QRY_ALL_ROUNDS_FOR_QUIZ = "qry_all-rounds-for-quiz";
    public static final String PARAMVALUE_QRY_ALL_TEAMS_FOR_QUIZ = "qry_all-teams-for-quiz";
    //Request ID's
    //GET Requests
    public static final int REQUEST_ID_GET_QUIZLIST = 0;
    public static final int REQUEST_ID_GET_ROUNDS = 1;
    public static final int REQUEST_ID_GET_QUESTIONS = 2;
    public static final int REQUEST_ID_GET_TEAMS = 3;
    public static final int REQUEST_ID_GET_ORGANIZERS = 4;
    public static final int REQUEST_ID_GET_ANSWERS = 5;
    public static final int REQUEST_ID_GET_ANSWERSSUBMITTED = 6;
    public static final int REQUEST_ID_GET_EVENTLOGS = 7;
    //Submit requests
    public static final int REQUEST_ID_AUTHENTICATE = 101;

    //Organizer Types
    public static final int ORGANIZERTYPE_QUIZMASTER = 0;
    public static final int ORGANIZERTYPE_RECEPTIONIST = 1;
    public static final int ORGANIZERTYPE_CORRECTOR = 2;
    public static final int ORGANIZERTYPE_JUROR = 3;


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
    //Column names table Organizers
    public static final String COLNAME_ID_ORGANIZER = "idOrganizer";
    public static final String COLNAME_ORGANIZER_NR = "OrganizerNr";
    public static final String COLNAME_ORGANIZER_TYPE = "OrganizerType";
    public static final String COLNAME_ORGANIZER_PASSWORD = "OrganizerPassword";
    public static final String COLNAME_ORGANIZER_NAME = "OrganizerName";
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
    //Column names table Teams
    public static final String COLNAME_ID_TEAM = "idTeam";
    public static final String COLNAME_TEAM_NR = "TeamNr";
    public static final String COLNAME_TEAM_STATUS = "TeamStatus";
    public static final String COLNAME_TEAM_PASSWORD = "TeamPassword";
    public static final String COLNAME_TEAM_NAME = "TeamName";

}
