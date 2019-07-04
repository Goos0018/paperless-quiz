package com.paperlessquiz.quiz;

import android.content.Context;

import com.paperlessquiz.googleaccess.GoogleAccess;
import com.paperlessquiz.googleaccess.GoogleAccessSet;
import com.paperlessquiz.googleaccess.LoadingListenerNotify;
import com.paperlessquiz.googleaccess.LoadingListenerShowProgress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuizGenerator {
    //TODO: set data as plain text!!

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
    public static final String EVENTLOG_LEVEL = "Level";
    public static final String EVENTLOG_MESSAGE = "Message";

    //Headers for the Organizers and Teams tabs of the Quiz sheet
    public static final String LOGIN_ENTITY_ID = "ID";
    public static final String LOGIN_ENTITY_TYPE = "Type";
    public static final String LOGIN_ENTITY_NAME = "Name";
    public static final String LOGIN_ENTITY_PASSKEY = "Passkey";
    public static final String TEAM_PRESENT = "Present";
    public static final String TEAM_LOGGED_IN = "LoggedIn";
    public static final int TEAMSHEET_START_OF_ROUNDS = 6; //From this column onwards, we expect to find the rounds in order of roundNr
    public static final String ROUNDS_PREFIX = "Rnd"; //This string is used as prefix for the round numbers when used as column headers

    //Headers for the Questions tab of the Quiz sheet (not matching previous headers)
    public static final String QUESTION_NAME = "Name";
    public static final String QUESTION_HINT = "Hint";
    public static final String QUESTION_FULL = "Question";
    public static final String QUESTION_CORRECT_ANSWER = "CorrectAnswer";
    public static final String QUESTION_MAX_SCORE = "MaxScore";

    //Headers for the Rounds tab of the Quiz sheet (not matching previous headers)
    public static final String ROUND_NAME = "RoundName";
    public static final String ROUND_DESCRIPTION = "RoundDescription";
    public static final String ROUND_NR_OF_QUESTIONS = "RoundNrOfQuestions";
    public static final String ROUND_ACCEPTS_ANSWERS = "RoundAcceptsAnswers";
    public static final String ROUND_ACCEPTS_CORRECTIONS = "RoundAcceptsCorrections";
    public static final String ROUND_IS_CORRECTED = "RoundIsCorrected";

    //Headers for the Scores sheet (not matching previous headers) - TODO: re-use for standings
    public static final String SCORE_TOTAL = "Total";
    public static final String SCORE_RND_INDICATOR = "Round";
    public static final int SCORESHEET_START_OF_ROUNDS = 4;

    //Headers for the QuizListData sheet
    public static final String QUIZ_NAME = "QuizName";
    public static final String QUIZ_DESCRIPTION = "QuizDescription";
    public static final String QUIZ_SHEET_DOC_ID = "QuizSheetDocID";
    public static final String QUIZ_LOGO_URL = "QuizLogoURL";
    public static final String QUIZ_DEBUGLEVEL = "DebugLevel";
    public static final String QUIZ_KEEPLOGS = "KeepLogs";
    public static final String QUIZ_APPDEBUGLEVEL = "AppDebugLevel";

    //Names of the different mandatory tabs in a Quiz sheet
    public static final String SHEET_ANSWERS = "Answers";
    public static final String SHEET_CORRECTIONS = "Corrections";
    public static final String SHEET_EVENTLOG = "EventLog";
    public static final String SHEET_ORGANIZERS = "Organizers";
    public static final String SHEET_QUESTIONS = "Questions";
    public static final String SHEET_ROUNDS = "Rounds";
    public static final String SHEET_SCORES = "Scores";
    public static final String SHEET_TEAMS = "Teams";
    public static final String SHEET_QUIZDATA = "QuizData";
    public static final String SHEET_QUIZLISTDATA = "QuizListData";


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
    public static final String TYPE_JUROR = "Juror";
    //Used to create dummy question names, team names etc
    public static final String FULL_QUESTION_STRING = "Full question ";
    public static final String HINT_STRING = "hint ";
    public static final String ROUND_DESCRIPTION_STRING = "Description for round ";
    public static final String TEAM_STRING = "Team ";

    //This is the ID and Tab of the (fixed) Google sheet that contains the list of quizzes to select from
    public static final String QUIZLIST_DOC_ID = "1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk";
    public static final String SHEET_QUIZLIST = "QuizList";
    //Debuglevel to use when creating stuff
    public static final int debugLevel = 5;

    //This is what we need to generate all mandatory tabs - these data are entered in the activity X_GenerateQuiz
    public String quizDocID; //Initialized when sheet is created
    public String quizName, quizDescription, quizLogoURL;
    public int quizDebugLevelDefault = 0;
    public int quizAppDebugLevelDefault = 0;
    public boolean quizKeepLogsDefault = false;
    private int standardScore = 2;
    private int nrOfRounds;
    private ArrayList<Integer> roundNrOfQuestions;
    private ArrayList<String> roundDescriptions;
    private int nrOfTeams;

    //Hardcoded for now
    private int nrOfCorrectors = 1;
    private Context context;


    private String quizMasterName = "Sven";
    private String quizMasterPw = "sven";
    private String jurorName = "Koen";
    private String jurorPw = "koen";
    private String receptionistName = "Dirk";
    private String receptionistPw = "dirk";
    private ArrayList<String> correctorNames;
    private String correctorPw;
    private ArrayList<String> allTabs = new ArrayList<>();
    private ArrayList<String> headersForQuizListDataTab = new ArrayList<>(); //To be copied to the QuizList Sheet manually to see the quiz in the list of a available quizzes
    private ArrayList<String> headersForAnswersTab = new ArrayList<>(); //Used for Answers and corrections
    private ArrayList<String> headersForEventLogTab = new ArrayList<>();
    private ArrayList<String> headersForOrganizersTab = new ArrayList<>();
    private ArrayList<String> headersForQuestionsTab = new ArrayList<>();
    private ArrayList<String> headersForRoundsTab = new ArrayList<>();
    private ArrayList<String> headersForScoresTab = new ArrayList<>();
    private ArrayList<String> headersForTeamsTab = new ArrayList<>();
    public ArrayList<ArrayList<String>> stdContentForQuizListDataTab = new ArrayList<>(); //This is set when the quizDoc is generated
    private ArrayList<ArrayList<String>> stdContentForAnswersTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForOrganizersTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForQuestionsTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForRoundsTab = new ArrayList<>();
    //private ArrayList<ArrayList<String>> stdContentForScoresTab = new ArrayList<>();
    private ArrayList<ArrayList<String>> stdContentForTeamsTab = new ArrayList<>();
    public GoogleAccessSet googleAccessCreateQuiz;

    //General settings for quiz generation
    public boolean setDummyAnswers = true;
    public boolean generateDummyPIN = true;
    private String dummyPin = "dummy";
    private int nrOfDigitsForParticipantPIN = 3;
    private int nrOfDigitsForOrganizersPIN = 6;


    public void createQuiz() {
        createQuizDoc(quizName, allTabs);
        //Rest of the actions is triggered by loadingComplete when quizDoc has been created
    }

    //Set all headers for all sheets - assumes quizDocID is properly initialized
    public void setAllHeaders() {
        setHeaders(SHEET_ANSWERS, headersForAnswersTab);
        setHeaders(SHEET_CORRECTIONS, headersForAnswersTab);
        setHeaders(SHEET_EVENTLOG, headersForEventLogTab);
        setHeaders(SHEET_ORGANIZERS, headersForOrganizersTab);
        setHeaders(SHEET_QUESTIONS, headersForQuestionsTab);
        setHeaders(SHEET_ROUNDS, headersForRoundsTab);
        setHeaders(SHEET_SCORES, headersForScoresTab);
        setHeaders(SHEET_TEAMS, headersForTeamsTab);
        setHeaders(SHEET_QUIZLISTDATA, headersForQuizListDataTab);
    }

    public void initializeAllSheets() {
        initializeRows(SHEET_ANSWERS, stdContentForAnswersTab);
        initializeRows(SHEET_CORRECTIONS, stdContentForAnswersTab);
        initializeRows(SHEET_ORGANIZERS, stdContentForOrganizersTab);
        initializeRows(SHEET_QUESTIONS, stdContentForQuestionsTab);
        initializeRows(SHEET_ROUNDS, stdContentForRoundsTab);
        initializeRows(SHEET_TEAMS, stdContentForTeamsTab);
        initializeRows(SHEET_QUIZLISTDATA, stdContentForQuizListDataTab);
    }


    //Create an empty document with the necessary tabs and return the quizDocID for it
    public void createQuizDoc(String quizName, ArrayList<String> sheetsToCreate) {
        String parameters = "sheetsToCreate=" + convertArrayListTo1DString(sheetsToCreate) + GoogleAccess.PARAM_CONCATENATOR +
                "quizName=" + quizName + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + "createQuizDoc";
        googleAccessCreateQuiz = new GoogleAccessSet(context, parameters);
        googleAccessCreateQuiz.setData(new LoadingListenerShowProgress(context, "Creating quiz", "Creating " + quizName, "Error", false));
        //The quizDocID for the quiz will be in the result property of the googleAccessCreateQuiz
    }

    //Set headers for the given sheet
    public void setHeaders(String sheetName, ArrayList<String> headersToSet) {
        String parameters = "docID=" + quizDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheetName + GoogleAccess.PARAM_CONCATENATOR +
                "headersToSet=" + convertArrayListTo2DString(headersToSet) + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + "setHeaders";
        GoogleAccessSet googleAccessSet = new GoogleAccessSet(context, parameters);
        googleAccessSet.setData(new LoadingListenerNotify(context, "Rupert", "Setting headers for sheet " + sheetName));
    }

    //Initialize rows for the given sheet
    public void initializeRows(String sheetName, ArrayList<ArrayList<String>> dataToSet) {
        String parameters = "docID=" + quizDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheetName + GoogleAccess.PARAM_CONCATENATOR +
                "dataToSet=" + convert2DArrayListToString(dataToSet) + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + "initializeRows";
        GoogleAccessSet googleAccessSet = new GoogleAccessSet(context, parameters);
        googleAccessSet.setData(new LoadingListenerNotify(context, "Rupert", "Setting headers for sheet " + sheetName));
    }

    //Helper functions
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

    public String generatePIN(int nrOfFigures) {
        if (generateDummyPIN) {
            return dummyPin;
        } else {
            String tmp = "";
            Random rand = new Random();
            for (int i = 0; i < nrOfFigures; i++) {
                tmp = tmp + rand.nextInt(10);
            }
            return tmp;
        }
    }

    //Standard constructors, getters and setters for this class
    public QuizGenerator(Context context, String quizName, String quizDescription, int nrOfRounds, ArrayList<Integer> roundNrOfQuestions, int nrOfTeams) {
        this.nrOfRounds = nrOfRounds;
        this.roundNrOfQuestions = roundNrOfQuestions;
        this.nrOfTeams = nrOfTeams;
        this.context = context;
        this.quizName = quizName;
        this.quizDescription = quizDescription;
        this.quizLogoURL = "";
        allTabs = new ArrayList<>(Arrays.asList(SHEET_QUIZLISTDATA, SHEET_TEAMS, SHEET_ORGANIZERS, SHEET_ROUNDS, SHEET_QUESTIONS, SHEET_ANSWERS, SHEET_CORRECTIONS, SHEET_SCORES, SHEET_EVENTLOG));
        headersForQuizListDataTab = new ArrayList<>(Arrays.asList(QUIZ_NAME, QUIZ_DESCRIPTION, QUIZ_SHEET_DOC_ID, QUIZ_LOGO_URL, QUIZ_DEBUGLEVEL, QUIZ_KEEPLOGS,QUIZ_APPDEBUGLEVEL));
        headersForAnswersTab = new ArrayList<>(Arrays.asList(QUESTION_ID, ROUND_NR, QUESTION_NR));
        for (int i = 1; i < nrOfTeams + 1; i++) {
            headersForAnswersTab.add(TEAMS_PREFIX + i);
        }
        headersForEventLogTab = new ArrayList<>(Arrays.asList(EVENTLOG_TIME, EVENTLOG_NAME, EVENTLOG_LEVEL,EVENTLOG_MESSAGE));
        headersForOrganizersTab = new ArrayList<>(Arrays.asList(LOGIN_ENTITY_ID, LOGIN_ENTITY_TYPE, LOGIN_ENTITY_NAME, LOGIN_ENTITY_PASSKEY));
        headersForQuestionsTab = new ArrayList<>(Arrays.asList(QUESTION_ID, ROUND_NR, QUESTION_NR, QUESTION_NAME, QUESTION_HINT, QUESTION_FULL, QUESTION_CORRECT_ANSWER, QUESTION_MAX_SCORE));
        headersForRoundsTab = new ArrayList<>(Arrays.asList(ROUND_NR, ROUND_NAME, ROUND_DESCRIPTION, ROUND_NR_OF_QUESTIONS, ROUND_ACCEPTS_ANSWERS, ROUND_ACCEPTS_CORRECTIONS, ROUND_IS_CORRECTED));
        headersForScoresTab = new ArrayList<>(Arrays.asList(LOGIN_ENTITY_ID, LOGIN_ENTITY_NAME));//TODO: complete + refacto to standings
        headersForTeamsTab = new ArrayList<>(Arrays.asList(LOGIN_ENTITY_ID, LOGIN_ENTITY_TYPE, LOGIN_ENTITY_NAME, LOGIN_ENTITY_PASSKEY, TEAM_PRESENT, TEAM_LOGGED_IN));
        for (int i = 1; i < nrOfRounds + 1; i++) {
            headersForTeamsTab.add(ROUNDS_PREFIX + i);
        }
        //stdContentForAnswersTab
        for (int i = 0; i < nrOfRounds; i++) {
            int roundNr = i + 1;
            for (int j = 0; j < roundNrOfQuestions.get(i); j++) {
                int questionNr = j + 1;
                //For question questionNr of round roundNr
                ArrayList<String> record = new ArrayList<>(Arrays.asList(roundNr + SEPARATOR_ROUND_QUESTION + questionNr, "" + roundNr, "" + questionNr));
                if (setDummyAnswers) {
                    for (int k = 1; k < nrOfTeams + 1; k++) {
                        record.add("Answer" + roundNr + SEPARATOR_ROUND_QUESTION + questionNr + " for team " + k);
                    }
                }
                stdContentForAnswersTab.add(record);
            }
        }
        //stdContentForOrganizersTab
        stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("1", TYPE_QUIZMASTER, TYPE_QUIZMASTER, generatePIN(nrOfDigitsForOrganizersPIN))));
        stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("2", TYPE_JUROR, TYPE_JUROR, generatePIN(nrOfDigitsForOrganizersPIN))));
        stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("3", TYPE_RECEPTIONIST, TYPE_RECEPTIONIST, generatePIN(nrOfDigitsForOrganizersPIN))));
        for (int i = 0; i < nrOfCorrectors; i++) {
            stdContentForOrganizersTab.add(new ArrayList<>(Arrays.asList("" + (i + 4), TYPE_CORRECTOR, TYPE_CORRECTOR, generatePIN(nrOfDigitsForOrganizersPIN))));
        }
        //stdContentForQuestionsTab
        for (int i = 0; i < nrOfRounds; i++) {
            int roundNr = i + 1;
            for (int j = 0; j < roundNrOfQuestions.get(i); j++) {
                int questionNr = j + 1;
                //For question questionNr of round roundNr
                stdContentForQuestionsTab.add(new ArrayList<>(Arrays.asList(roundNr + SEPARATOR_ROUND_QUESTION + questionNr, "" + roundNr, "" + questionNr,
                        QUESTION_STRING + questionNr, "(" + HINT_STRING + roundNr + SEPARATOR_ROUND_QUESTION + questionNr + ")",
                        FULL_QUESTION_STRING + roundNr + SEPARATOR_ROUND_QUESTION + questionNr, "(correct answer)", Integer.toString(standardScore))));
            }
        }
        //stdContentForRoundsTab
        for (int i = 0; i < nrOfRounds; i++) {
            int roundNr = i + 1;
            stdContentForRoundsTab.add(new ArrayList<>(Arrays.asList("" + roundNr, ROUND_STRING + roundNr, ROUND_DESCRIPTION_STRING + roundNr, "" + roundNrOfQuestions.get(i))));
        }
        //stdContentForTeamsTab
        for (int i = 0; i < nrOfTeams; i++) {
            int teamnr = i + 1;
            stdContentForTeamsTab.add(new ArrayList<>(Arrays.asList("" + teamnr, TYPE_PARTICIPANT, TEAM_STRING + teamnr, generatePIN(nrOfDigitsForParticipantPIN))));
        }
        //stdContentForQuizListDataTab => This can only be set when teh docID has been generated
        //stdContentForQuizListDataTab.add(new ArrayList<>(Arrays.asList(quizName, quizDescription, quizDocID, quizLogoURL, Integer.toString(quizDebugLevelDefault), Boolean.toString(quizKeepLogsDefault))));
    }

    public void setQuizDocID(String quizDocID) {
        this.quizDocID = quizDocID;
    }
}

