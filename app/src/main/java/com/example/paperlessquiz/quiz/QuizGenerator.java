package com.example.paperlessquiz.quiz;

import android.content.Context;

import com.example.paperlessquiz.google.access.GoogleAccessSet;

import java.util.ArrayList;

public class QuizGenerator {
    //Headers for the Answers tab of the Quiz sheet
    //Headers for the Corrections sheet - these are identical to those of the answers sheet
    public static final String QUESTION_ID = "QuestionID";
    public static final String ROUND_NR = "RoundNr";
    public static final String QUESTION_NR = "QuestionNr";
    public static final int ANSWERSSHEET_START_OF_TEAMS = 3; //From this column onwards, we expect to find the teams in order of teamnr
    public static final String TEAMS_PREFIX = ""; //This string is used as prefix for the team numbers when used as column headers


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

    //This is the ID and Tab of the (fixed) Google sheet that contains the list of quizzes to select from
    public static final String QUIZLIST_DOC_ID = "1A4CGyeZZk2LW-xvh_P1dyeufZhV0qpBgCIQdrNEIDgk";
    public static final String SHEET_QUIZLIST = "QuizList";

    //This is what we need to generate all mandatory tabs - these data are entered in the activity X_GenerateQuiz
    private int nrOfRounds;
    private ArrayList<Integer> nrOfQuestionsForRound;
    private int nrOfTeams;
    private Context context;

    //Set headers for the Answers sheet

    //Create an empty document with the necessary tabs
    public String createQuizDoc(ArrayList<String> sheetsToCreate) {
        String docID = ""; //TODO: return from Google script
        return docID;
    }

    //Standard constructors, getters and setters for this class
    public QuizGenerator(Context context, int nrOfRounds, ArrayList<Integer> nrOfQuestionsForRound, int nrOfTeams) {
        this.nrOfRounds = nrOfRounds;
        this.nrOfQuestionsForRound = nrOfQuestionsForRound;
        this.nrOfTeams = nrOfTeams;
        this.context = context;
    }
}
