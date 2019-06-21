package com.example.paperlessquiz.quiz;

import android.content.Context;

import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.google.access.LoadingListenerShowProgress;
import com.example.paperlessquiz.google.access.LoadingListenerSilent;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizGenerator {
    //Headers for the Answers tab of the Quiz sheet
    //Headers for the Corrections sheet - these are identical to those of the answers sheet
    public static final String QUESTION_ID = "QuestionID";
    public static final String ROUND_NR = "RoundNr";
    public static final String QUESTION_NR = "QuestionNr";
    public static final int ANSWERSSHEET_START_OF_TEAMS = 3; //From this column onwards, we expect to find the teams in order of teamnr
    public static final String TEAMS_PREFIX = ""; //This string is used as prefix for the team numbers when used as column headers

    //Headers for the Eventlog sheet
    public static final String EVENTLOG_TIME = "Date/Time";
    public static final String EVENTLOG_NAME = "Teamname";
    public static final String EVENTLOG_MESSAGE = "Message";

    //Headers for the Organizers and Teams tabs of the Quiz sheet
    public static final String LOGIN_ENTITY_ID = "ID";
    public static final String LOGIN_ENTITY_TYPE = "Type";
    public static final String LOGIN_ENTITY_NAME = "Name";
    public static final String LOGIN_ENTITY_PASSKEY = "Passkey";
    public static final String TEAM_PRESENT = "Present";
    public static final String TEAM_LOGGED_IN = "LoggedIn";
    public static final int TEAMSHEET_START_OF_ROUNDS = 6; //From this column onwards, we expect to find the rounds in order of roundNr
    public static final String ROUNDS_PREFIX = ""; //This string is used as prefix for the round numbers when used as column headers

    //Headers for the Questions tab of the Quiz sheet (not matching previous headers)
    //public static final String QUESTIONID = "QuestionID";
    //public static final String QUESTIONNR = "QuestionNr";
    //public static final String ROUND_ID = "RoundNr";
    public static final String QUESTION_NAME = "Name";
    public static final String QUESTION_HINT = "Hint";
    public static final String QUESTION_FULL = "Question";
    public static final String QUESTION_CORRECT_ANSWER = "CorrectAnswer";
    public static final String QUESTION_MAX_SCORE = "MaxScore";

    //Headers for the Rounds tab of the Quiz sheet (not matching previous headers)
    //public static final String ROUND_NR = "RoundNr";
    public static final String ROUND_NAME = "RoundName";
    public static final String ROUND_DESCRIPTION = "RoundDescription";
    public static final String ROUND_NR_OF_QUESTIONS = "RoundNrOfQuestions";
    public static final String ROUND_ACCEPTS_ANSWERS = "RoundAcceptsAnswers";
    public static final String ROUND_ACCEPTS_CORRECTIONS = "RoundAcceptsCorrections";
    public static final String ROUND_IS_CORRECTED = "RoundIsCorrected";

    //Headers for the Scores sheet (not matching previous headers) - TODO: re-use for standings
    //public static final String SCORE_TEAMID = "TeamID";
    //public static final String SCORE_TEAMNAME = "Name";
    public static final String SCORE_TOTAL = "Total";
    public static final String SCORE_RND_INDICATOR = "Round";
    public static final int SCORESHEET_START_OF_ROUNDS = 4;

    //Names of the different mandatory tabs in a Quiz sheet
    public static final String SHEET_ANSWERS = "Answers";
    public static final String SHEET_CORRECTIONS = "Corrections";
    public static final String SHEET_EVENTLOG = "EventLog";
    public static final String SHEET_ORGANIZERS = "Organizers";
    public static final String SHEET_QUESTIONS = "Questions";
    public static final String SHEET_ROUNDS = "Rounds";
    public static final String SHEET_SCORES = "Scores";
    public static final String SHEET_TEAMS = "Teams";
    public static final String SHEET_QUIZDATA = "QuizData"; //TODO: remove this, not needed anymore

    //Other static stuff
    public static final String SEPARATOR_ROUND_QUESTION = ".";
    public static final String QUESTION_STRING = "Vraag ";
    public static final String ROUND_STRING = "Ronde ";
    public static final String SELECTION_PARTICIPANT = "Participant";
    public static final String SELECTION_ORGANIZER = "Organizer";
    public static final String TYPE_PARTICIPANT = "Participant";
    public static final String TYPE_CORRECTOR = "Corrector";
    public static final String TYPE_QUIZMASTER = "Quizmaster";
    public static final String TYPE_RECEPTIONIST = "Receptionist";
    public static final String EMPTY_TEAMNAME = "Enter teamname";

    //This is the ID and Tab of the (fixed) Google sheet that contains the list of quizzes to select from
    public static final String QUIZLIST_DOC_ID = "1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk";
    public static final String SHEET_QUIZLIST = "QuizList";
    //Debuglevel to use when creating stuff
    public static final int debugLevel = 5;

    //This is what we need to generate all mandatory tabs - these data are entered in the activity X_GenerateQuiz

    private String quizName;
    private int nrOfRounds;
    private ArrayList<Integer> roundNrOfQuestions;
    private ArrayList<String> roundDescriptions;
    private int nrOfTeams;
    public String docID; //Initialized when sheet is created
    //Hardcoded for now
    private int nrOfCorrectors = 1;
    private Context context;

    private int standardScore = 2;
    private String quizMasterName = "Sven";
    private String quizMasterPw = "sven";
    private String jurorName = "Koen";
    private String jurorPw = "koen";
    private String receptionistName = "Dirk";
    private String receptionistPw = "dirk";
    private ArrayList<String> correctorNames;
    private String correctorPw;
    private ArrayList<String> allTabs = new ArrayList<>();
    private ArrayList<String> headersForAnswersTab = new ArrayList<>(); //Used for Answers and corrections
    private ArrayList<String> headersForEventLogTab = new ArrayList<>();
    private ArrayList<String> headersForOrganizersTab = new ArrayList<>();
    private ArrayList<String> headersForQuestionsTab = new ArrayList<>();
    private ArrayList<String> headersForRoundsTab = new ArrayList<>();
    private ArrayList<String> headersForScoresTab = new ArrayList<>();
    private ArrayList<String> headersForTeamsTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForAnswersTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForOrganizersTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForQuestionsTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForRoundsTab = new ArrayList<>();
    //private ArrayList<ArrayList<String>> stdContentForScoresTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForTeamsTab = new ArrayList<>();
    public GoogleAccessSet googleAccessCreateQuiz;



    public void createQuiz(){
        createQuizDoc(quizName,allTabs);
        //Rest of the actions is triggered by loadingComplete when quizDoc has been created
    }

    //Set all headers for all sheets - assumes docID is properly initialized
    public void setAllHeaders() {
        setHeaders(SHEET_ANSWERS, headersForAnswersTab);
        setHeaders(SHEET_CORRECTIONS, headersForAnswersTab);
        setHeaders(SHEET_EVENTLOG, headersForEventLogTab);
        setHeaders(SHEET_ORGANIZERS, headersForOrganizersTab);
        setHeaders(SHEET_QUESTIONS, headersForQuestionsTab);
        setHeaders(SHEET_ROUNDS, headersForRoundsTab);
        setHeaders(SHEET_SCORES, headersForScoresTab);
        setHeaders(SHEET_TEAMS, headersForTeamsTab);
    }

    public void initializeAllSheets() {
        initializeRows(SHEET_ANSWERS, stdContentForAnswersTab);
        initializeRows(SHEET_CORRECTIONS, stdContentForAnswersTab);
        //initializeRows(SHEET_EVENTLOG, headersForEventLogTab);
        initializeRows(SHEET_ORGANIZERS, stdContentForOrganizersTab);
        initializeRows(SHEET_QUESTIONS, stdContentForQuestionsTab);
        initializeRows(SHEET_ROUNDS, stdContentForRoundsTab);
        //initializeRows(SHEET_SCORES, headersForScoresTab);
        initializeRows(SHEET_TEAMS, stdContentForTeamsTab);
    }


    //Create an empty document with the necessary tabs and return the docID for it
    public void createQuizDoc(String quizName, ArrayList<String> sheetsToCreate) {
        String parameters = "sheetsToCreate=" + convertArrayListTo1DString(sheetsToCreate) + GoogleAccess.PARAM_CONCATENATOR +
                "quizName=" + quizName + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + "createQuizDoc";
        googleAccessCreateQuiz = new GoogleAccessSet(context, parameters, debugLevel);
        googleAccessCreateQuiz.setData(new LoadingListenerShowProgress(context,"Creating quiz","Creating " + quizName,"Error",false));
        //The docID for the quiz will be in the result property of the googleAccessCreateQuiz
    }

    //Set headers for the given sheet
    public void setHeaders(String sheetName, ArrayList<String> headersToSet) {
        String parameters = "docID=" + docID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheetName + GoogleAccess.PARAM_CONCATENATOR +
                "headersToSet=" + convertArrayListTo2DString(headersToSet) + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + "setHeaders";
        GoogleAccessSet googleAccessSet = new GoogleAccessSet(context, parameters, debugLevel);
        googleAccessSet.setData(new LoadingListenerNotify(context, "Rupert", "Setting headers for sheet " + sheetName));
    }

    //Initialize rows for the given sheet
    public void initializeRows(String sheetName, ArrayList<ArrayList<String>> dataToSet) {
        String parameters = "docID=" + docID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheetName + GoogleAccess.PARAM_CONCATENATOR +
                "dataToSet=" + convert2DArrayListToString(dataToSet) + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + "initializeRows";
        GoogleAccessSet googleAccessSet = new GoogleAccessSet(context, parameters, debugLevel);
        googleAccessSet.setData(new LoadingListenerNotify(context, "Rupert", "Setting headers for sheet " + sheetName));
    }

    //Convert ArrayList to something like [["value1","value2",...,"valuen"]]
    public String convertArrayListTo2DString(ArrayList<String> arrayListToConvert) {
        ArrayList<ArrayList<String>> tmp2DArraylist = new ArrayList<>();
        tmp2DArraylist.add(arrayListToConvert);
        return convert2DArrayListToString(tmp2DArraylist);
        /*
        String tmp = "[[";
        for (int i = 0; i < arrayListToConvert.size(); i++) {
            tmp = tmp + "\"" + arrayListToConvert.get(i) + "\"";
            if (i == arrayListToConvert.size() - 1) {
                tmp = tmp + "]]";
            } else {
                tmp = tmp + ",";
            }
        }
        return tmp;
        */
    }

    //Convert ArrayList to something like ["value1","value2",...,"valuen"]
    public String convertArrayListTo1DString(ArrayList<String> arrayListToConvert) {

        String tmp = "[";
        for (int i = 0; i < arrayListToConvert.size(); i++) {
            tmp = tmp + "\"" + arrayListToConvert.get(i) + "\"";
            if (i == arrayListToConvert.size() - 1) {
                tmp = tmp + "]";
            } else {
                tmp = tmp + ",";
            }
        }
        return tmp;

    }

    //Convert ArrayList to something like [["value1","value2",...,"valuen"],["value1","value2",...,"valuen"],...,["value1","value2",...,"valuen"]]
    public String convert2DArrayListToString(ArrayList<ArrayList<String>> arrayListToConvert) {
        String tmp = "[[";
        //For each row
        for (int i = 0; i < arrayListToConvert.size(); i++) {
            ArrayList<String> row = arrayListToConvert.get(i);
            //for each field in the row
            for (int j = 0; j < row.size(); j++) {
                tmp = tmp + "\"" + row.get(j) + "\"";
                if (j == row.size() - 1) {                          //If this is the last field in the row
                    if (i == arrayListToConvert.size() - 1) {       //If this is the last row
                        tmp = tmp + "]]";
                    } else {
                        tmp = tmp + "],[";
                    }
                } else {
                    tmp = tmp + ",";
                }
            }
        }
        return tmp;
    }

    //Standard constructors, getters and setters for this class
    public QuizGenerator(Context context, String quizName, int nrOfRounds, ArrayList<Integer> roundNrOfQuestions, int nrOfTeams) {
        this.nrOfRounds = nrOfRounds;
        this.roundNrOfQuestions = roundNrOfQuestions;
        this.nrOfTeams = nrOfTeams;
        this.context = context;
        this.quizName=quizName;
        allTabs.add(SHEET_ANSWERS);
        allTabs.add(SHEET_CORRECTIONS);
        allTabs.add(SHEET_EVENTLOG);
        allTabs.add(SHEET_ORGANIZERS);
        allTabs.add(SHEET_QUESTIONS);
        allTabs.add(SHEET_ROUNDS);
        allTabs.add(SHEET_SCORES);
        allTabs.add(SHEET_TEAMS);
        headersForAnswersTab.add(QUESTION_ID);
        headersForAnswersTab.add(ROUND_NR);
        headersForAnswersTab.add(QUESTION_NR);
        for (int i = 1; i < nrOfTeams+1; i++) {
            headersForAnswersTab.add(TEAMS_PREFIX + i);
        }
        headersForEventLogTab.add(EVENTLOG_TIME);
        headersForEventLogTab.add(EVENTLOG_NAME);
        headersForEventLogTab.add(EVENTLOG_MESSAGE);
        headersForOrganizersTab.add(LOGIN_ENTITY_ID);
        headersForOrganizersTab.add(LOGIN_ENTITY_TYPE);
        headersForOrganizersTab.add(LOGIN_ENTITY_NAME);
        headersForOrganizersTab.add(LOGIN_ENTITY_PASSKEY);
        headersForQuestionsTab.add(QUESTION_ID);
        headersForQuestionsTab.add(ROUND_NR);
        headersForQuestionsTab.add(QUESTION_NR);
        headersForQuestionsTab.add(QUESTION_NAME);
        headersForQuestionsTab.add(QUESTION_HINT);
        headersForQuestionsTab.add(QUESTION_FULL);
        headersForQuestionsTab.add(QUESTION_CORRECT_ANSWER);
        headersForQuestionsTab.add(QUESTION_MAX_SCORE);
        headersForRoundsTab.add(ROUND_NR);
        headersForRoundsTab.add(ROUND_NAME);
        headersForRoundsTab.add(ROUND_DESCRIPTION);
        headersForRoundsTab.add(ROUND_NR_OF_QUESTIONS);
        headersForRoundsTab.add(ROUND_ACCEPTS_ANSWERS);
        headersForRoundsTab.add(ROUND_ACCEPTS_CORRECTIONS);
        headersForRoundsTab.add(ROUND_IS_CORRECTED);
        headersForScoresTab.add(LOGIN_ENTITY_ID);
        headersForScoresTab.add(LOGIN_ENTITY_NAME);//TODO: complete
        headersForTeamsTab.add(LOGIN_ENTITY_ID);
        headersForTeamsTab.add(LOGIN_ENTITY_TYPE);
        headersForTeamsTab.add(LOGIN_ENTITY_NAME);
        headersForTeamsTab.add(LOGIN_ENTITY_PASSKEY);
        headersForTeamsTab.add(TEAM_PRESENT);
        headersForTeamsTab.add(TEAM_LOGGED_IN);
        for (int i = 1; i < nrOfRounds+1; i++) {
            headersForTeamsTab.add(ROUNDS_PREFIX + i);
        }
        //stdContentForAnswersTab
        for (int i = 0; i < nrOfRounds; i++) {
            int roundNr = i + 1;
            for (int j = 0; j < roundNrOfQuestions.get(i); j++) {
                int questionNr = j + 1;
                //For question questionNr of round roundNr
                ArrayList<String> record = new ArrayList<>();
                record.add(roundNr + SEPARATOR_ROUND_QUESTION + questionNr);
                record.add("" + roundNr);
                record.add("" + questionNr);
                stdContentForAnswersTab.add(record);
            }
        }
        //stdContentForOrganizersTab
        stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("1", TYPE_QUIZMASTER, quizMasterName, quizMasterPw)));
        stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("2", "Juror", jurorName, jurorPw)));
        stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("3", TYPE_RECEPTIONIST, receptionistName, receptionistPw)));
        for (int i = 0; i < nrOfCorrectors; i++) {
            stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("" + (i + 4), TYPE_CORRECTOR, "Corrector", correctorPw)));
        }
        //stdContentForQuestionsTab
        for (int i = 0; i < nrOfRounds; i++) {
            int roundNr = i + 1;
            for (int j = 0; j < roundNrOfQuestions.get(i); j++) {
                int questionNr = j + 1;
                //For question questionNr of round roundNr
                stdContentForAnswersTab.add(new ArrayList<>(Arrays.asList(roundNr + SEPARATOR_ROUND_QUESTION + questionNr, "" + roundNr, "" + questionNr,
                        QUESTION_STRING + roundNr + SEPARATOR_ROUND_QUESTION + questionNr, "", "", "", QUESTION_MAX_SCORE)));
            }
        }
        //stdContentForRoundsTab
        for (int i = 0; i < nrOfRounds; i++) {
            int roundNr = i + 1;
            stdContentForRoundsTab.add(new ArrayList<>(Arrays.asList("" + roundNr, ROUND_STRING + roundNr, "description" + roundNr, "" + roundNrOfQuestions.get(i))));
        }
        //stdContentForTeamsTab
        for (int i = 0; i < nrOfTeams; i++) {
            int teamnr = i + 1;
            stdContentForTeamsTab.add(new ArrayList<>(Arrays.asList("" + teamnr, TYPE_PARTICIPANT, "", "")));
        }
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }
}

