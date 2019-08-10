package com.paperlessquiz.quiz;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizDatabase {
    /**
     * Contains only constants reflecting the SQL db structure and usage here
     */

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
    public static final String PARAMNAME_ITEMSTOORDER = "itemsToOrder=";
    public static final String PARAMNAME_TIME = "time=";
    public static final String PARAMNAME_ORDERBY = "orderBy=";
    public static final String PARAMNAME_ORDERDIRECTION = "orderDirection=";
    public static final String PARAMNAME_ORDERCATEGORIES = "orderCategories=";
    public static final String PARAMNAME_ORDERSTATUSLIST = "orderStatusList=";
    public static final String PARAMNAME_ORDERUSERSLIST = "orderUsersList=";
    public static final String PARAMNAME_IDORDER = "idOrder=";
    public static final String PARAMNAME_NEWORDERSTATUS = "newOrderStatus=";

    // Available PHP scripts
    public static final String SCRIPT_GET_QUIZLIST = "getquizlist.php";
    public static final String SCRIPT_GET_QUIZUSERS = "getuserlist.php";
    public static final String SCRIPT_GET_QUIZDATA = "getquizdata.php";
    public static final String SCRIPT_AUTHENTICATE = "authenticate.php";
    public static final String SCRIPT_SUBMITANSWER = "submitanswer.php";
    public static final String SCRIPT_SETANSWERSSUBMITTED = "setanswerssubmitted.php";
    public static final String SCRIPT_UPDATEROUNDSTATUS = "updateroundstatus.php";
    public static final String SCRIPT_UPDATEMYSTATUS = "updatemystatus.php";
    public static final String SCRIPT_UPDATETEAM = "updateteam.php";
    public static final String SCRIPT_CORRECTQUESTION = "correctquestion.php";
    public static final String SCRIPT_CALCULATESCORESFORROUND = "calculatescoresforround.php";
    public static final String SCRIPT_SUBMITORDER = "createorder.php";
    public static final String SCRIPT_GET_ALLORDERS = "getallorders.php";
    public static final String SCRIPT_UPDATEORDERSTATUS = "updateorderstatus.php";
    public static final String SCRIPT_UPDATEEXISTINGORDER = "updateexistingorder.php";
    public static final String SCRIPT_LOCKORDERFORPREP = "lockorderforprep.php";
    //Param values - query names
    public static final String PARAMVALUE_QRY_ALL_ROUNDS = "rounds";
    public static final String PARAMVALUE_QRY_ALL_QUESTIONS = "questions";
    public static final String PARAMVALUE_QRY_MY_ANSWERS = "myanswers";
    public static final String PARAMVALUE_QRY_ALL_ANSWERS = "allanswers";
    public static final String PARAMVALUE_QRY_FULL_QUESTIONS = "fullquestions";
    public static final String PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED = "answerssubmitted";
    public static final String PARAMVALUE_QRY_MY_EVENTLOGS = "myevents";
    public static final String PARAMVALUE_QRY_SCORES = "scores";
    public static final String PARAMVALUE_QRY_ORDERITEMS = "orderitems";
    public static final String PARAMVALUE_QRY_ORDERSFORUSER = "ordersforuser";
    public static final String PARAMVALUE_QRY_ALLORDERS = "allorders";
    public static final String PARAMVALUE_QRY_ORDERDETAILS="orderdetails";
    public static final String PARAMVALUE_ORDERASC="ASC";
    public static final String PARAMVALUE_ORDERDESC="DESC";

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
    public static final int REQUEST_ID_GET_RESULTS = 9;
    public static final int REQUEST_ID_GET_ORDERITEMS = 10;
    public static final int REQUEST_ID_GET_ORDERSFORUSER = 11;
    public static final int REQUEST_ID_GET_ALLORDERS = 12;
    public static final int REQUEST_ID_GET_ORDERDETAILS = 13;
    //Submit requests
    public static final int REQUEST_ID_AUTHENTICATE = 100;
    public static final int REQUEST_ID_SUBMITANSWER = 101;
    public static final int REQUEST_ID_SETANSWERSSUBMITTED = 102;
    public static final int REQUEST_ID_UPDATEROUNDSTATUS = 103;
    public static final int REQUEST_ID_UPDATEMYSTATUS = 104;
    public static final int REQUEST_ID_UPDATETEAM = 105;
    public static final int REQUEST_ID_CORRECTQUESTION = 106;
    public static final int REQUEST_ID_CALCULATESCORES = 107;
    public static final int REQUEST_ID_SUBMITORDER = 108;
    public static final int REQUEST_ID_UPDATEEXISTINGORDER = 109;
    public static final int REQUEST_ID_UPDATEORDERSTATUS = 110;
    public static final int REQUEST_ID_LOCKORDERFORPREP = 111;


    //User types - these must match what is defined in the constants.php
    public static final int USERTYPE_TEAM = 0;
    public static final int USERTYPE_QUIZMASTER = 1;
    public static final int USERTYPE_RECEPTIONIST = 2;
    public static final int USERTYPE_CORRECTOR = 3;
    public static final int USERTYPE_JUROR = 4;
    public static final int USERTYPE_SALES = 5;
    public static final int USERTYPE_BARRESPONSIBLE = 6;
    public static final int USERTYPE_BARHELPER = 7;

    //User Statuses  - these must match what is defined in the constants.php
    public static final int USERSTATUS_NOTPRESENT = 0;
    public static final int USERSTATUS_PRESENTNOTLOGGEDIN = 1;
    public static final int USERSTATUS_PRESENTLOGGEDIN = 2;

    //Round Statuses
    public static final int ROUNDSTATUS_CLOSED = 0;
    public static final int ROUNDSTATUS_OPENFORANSWERS = 1;
    public static final int ROUNDSTATUS_OPENFORCORRECTIONS = 2;
    public static final int ROUNDSTATUS_CORRECTED = 3;

    //Order Statuses
    public static final int ORDERSTATUS_MODIFIED = 0;
    public static final int ORDERSTATUS_SUBMITTED = 1;
    public static final int ORDERSTATUS_INPROGRESS = 2;
    public static final int ORDERSTATUS_READY = 3;
    public static final int ORDERSTATUS_DELIVERED = 4;
    public static final String ORDERSTATUS_MODIFIED_STR = "Gewijzigd";
    public static final String ORDERSTATUS_SUBMITTED_STR = "Nieuw";
    public static final String ORDERSTATUS_INPROGRESS_STR = "Klaarmaken ... ";
    public static final String ORDERSTATUS_READY_STR = "Onderweg ...";
    public static final String ORDERSTATUS_DELIVERED_STR = "Afgeleverd";
    //Bar helper and repsonsible stuff
    public static final String BARHELPERROLENAME_PREPARE = "Bestellingen klaarzetten";
    public static final String BARHELPERROLENAME_DELIVER = "Bestellingen afleveren";
    public static final int BARHELPERROLECODE_NONE = 0;
    public static final int BARHELPERROLECODE_PREPARE = 1;
    public static final int BARHELPERROLECODE_DELIVER = 2;
    public static final int DIALOG_CATS = 1;
    public static final int DIALOG_STATUSES = 2;
    public static final int DIALOG_USERS = 3;

    public static final ArrayList<String> displayStatusesArray= new ArrayList<>(Arrays.asList(ORDERSTATUS_MODIFIED_STR,ORDERSTATUS_SUBMITTED_STR,
            ORDERSTATUS_INPROGRESS_STR,ORDERSTATUS_READY_STR,ORDERSTATUS_DELIVERED_STR));
    public static final ArrayList<String> statusesArray= new ArrayList<>(Arrays.asList(Integer.toString(ORDERSTATUS_MODIFIED),Integer.toString(ORDERSTATUS_SUBMITTED),
            Integer.toString(ORDERSTATUS_INPROGRESS),Integer.toString(ORDERSTATUS_READY),Integer.toString(ORDERSTATUS_DELIVERED)));
    public static final ArrayList<String> displayHelperRolesArray= new ArrayList<>(Arrays.asList(BARHELPERROLENAME_PREPARE, BARHELPERROLENAME_DELIVER));
    public static final ArrayList<String> helperRolesArray= new ArrayList<>(Arrays.asList(Integer.toString(ORDERSTATUS_SUBMITTED),Integer.toString(ORDERSTATUS_READY)));

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
    public static final String COLNAME_USER_NAME = "UserName";
    //Column names table ScoreForRound
    public static final String COLNAME_SCOREFORROUND = "ScoreForRound";
    //Column names table ScoreAfterRound
    public static final String COLNAME_SCOREAFTERROUND = "ScoreAfterRound";
    //Column names table StandingForRound
    public static final String COLNAME_STANDINGFORROUND = "StandingForRound";
    //Column names table StandingAfterRound
    public static final String COLNAME_STANDINGAFTERROUND = "StandingAfterRound";
    //Column names table OrderItem
    public static final String COLNAME_IDORDERITEM = "idOrderItem";
    public static final String COLNAME_ITEMCATEGORY = "ItemCategory";
    public static final String COLNAME_ITEMNAME = "ItemName";
    public static final String COLNAME_ITEMDESCRIPTION = "ItemDescription";
    public static final String COLNAME_ITEMCOST = "ItemCost";
    public static final String COLNAME_ITEMUNITSAVAIALABLE = "ItemUnitsAvailable";
    public static final String COLNAME_ITEMSOLDOUT = "ItemSoldOut";
    //Column names table Orders
    public static final String COLNAME_IDORDER = "idOrder";
    public static final String COLNAME_ORDERCATEGORY = "OrderCategory";
    public static final String COLNAME_ORDERNUMBER = "OrderNumber";
    public static final String COLNAME_ORDERCOST = "OrderCost";
    public static final String COLNAME_ORDERSTATUS = "OrderStatus";
    public static final String COLNAME_ORDERLASTUPDATE = "OrderLastUpdate";
    //Column names table ItemsOrdered
    public static final String COLNAME_ORDEREDAMOUNT = "ItemsOrderedAmount";

    //Helpfiles
    public static final String HELPFILE_PARTICIPANT = "ParticipantHelp.pdf";
    public static final String HELPFILE_CORRECTOR = "CorrectorHelp.pdf";
    public static final String HELPFILE_QUIZMASTER = "QuizmasterHelp.pdf";
    public static final String HELPFILE_RECEPTIONIST = "ReceptionistHelp.pdf";
    public static final String HELPFILE_JUROR = "JurorHelp.pdf";
    public static final String PDF_TO_DISPLAY = "pdfToDisplay";
    public static final String TITLE_FOR_PDF_DISPLAY = "TitleForPDFDisplay";
    public static final String GOOGLE_PDFVIEWER_URL = "https://docs.google.com/gview?embedded=true&url=";

    //Various other constants
    public static final String INTENT_EXTRANAME_IS_ORGANIZER = "isOrganizer";
    public static final String INTENT_EXTRANAME_ORDER_TO_EDIT = "orderToEdit";
    public static final String EURO_SIGN = "€ ";    //Used in ShowOrderAdapter
    public static final String TEAM = "Tafel ";         //Used in ShowOrderAdapter
    public static final int MAX_NAME_LENGTH = 10;         //Used in ShowOrderAdapter
    //public static final int REQUEST_CODE_ORDERHOME = 1;
    public static final int REQUEST_CODE_NEWORDER = 0;
    public static final int REQUEST_CODE_EDITEXISTINGORDER = 1;
}

