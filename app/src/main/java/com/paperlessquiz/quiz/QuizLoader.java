package com.paperlessquiz.quiz;

import android.content.Context;
import android.util.EventLog;

import com.paperlessquiz.AnswersSubmitted;
import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.corrections.CorrectionsList;
import com.paperlessquiz.corrections.CorrectionsListParser;
import com.paperlessquiz.corrections.GetCorrectionsListLPL;
import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.answer.AnswersList;
import com.paperlessquiz.answer.AnswersListParser;
import com.paperlessquiz.answer.GetAnswersListLPL;
import com.paperlessquiz.googleaccess.GoogleAccess;
import com.paperlessquiz.googleaccess.GoogleAccessGet;
import com.paperlessquiz.googleaccess.LLShowProgressActWhenComplete;
import com.paperlessquiz.loginentity.GetLoginEntriesLPL;
import com.paperlessquiz.loginentity.Organizer;
import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.loginentity.LoginEntityParser;
import com.paperlessquiz.parsers.AnswersParser;
import com.paperlessquiz.parsers.AnswersSubmittedParser;
import com.paperlessquiz.parsers.JsonParser;
import com.paperlessquiz.parsers.LogMessageParser;
import com.paperlessquiz.parsers.OrganizerParser;
import com.paperlessquiz.parsers.TeamParser;
import com.paperlessquiz.question.GetQuestionsLPL;
import com.paperlessquiz.question.Question;
import com.paperlessquiz.question.QuestionParser;
//import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.paperlessquiz.question.QuestionsList;
import com.paperlessquiz.round.GetRoundsLPL;
import com.paperlessquiz.round.Round;
import com.paperlessquiz.round.RoundParser;
import com.paperlessquiz.webrequest.HTTPGet;

import java.util.ArrayList;

//This class populates a quiz from a a Google Sheet
public class QuizLoader {
    private Context context;
    private String sheetDocID;
    private Quiz thisQuiz = MyApplication.theQuiz;
    //public GetQuizExtraDataLPL quizExtraDataLPL;
    public GetRoundsLPL quizRoundsLPL;
    public GetQuestionsLPL quizQuestionsLPL;
    public GetAnswersListLPL quizAnswersLPL;
    public GetCorrectionsListLPL quizCorrectionsLPL;
    public GetLoginEntriesLPL quizTeamsLPL, quizOrganizersLPL;
    public HTTPGet<Team> getTeamsRequest;
    public HTTPGet<Organizer> getOrganizersRequest;
    public HTTPGet<Answer> getAnswersRequest;
    public HTTPGet<Question> getQuestionsRequest;
    public HTTPGet<Round> getRoundsRequest;
    public HTTPGet<AnswersSubmitted> getAnswersSubmittedRequest;
    public HTTPGet<EventLog> getEventLogsRequest;

    //public GetScoresLPL quizScoresLPL;

    public ArrayList<ArrayList<Answer>> myAnswers;

    public QuizLoader(Context context, String sheetDocID) {
        this.context = context;
        this.sheetDocID = sheetDocID;
        myAnswers = new ArrayList<>();
        //this.quiz = MyApplication.theQuiz;
        //quizExtraDataLPL = new GetQuizExtraDataLPL();
        quizRoundsLPL = new GetRoundsLPL();
        quizQuestionsLPL = new GetQuestionsLPL();
        quizTeamsLPL = new GetLoginEntriesLPL();
        quizOrganizersLPL = new GetLoginEntriesLPL();
        quizAnswersLPL = new GetAnswersListLPL();
        quizCorrectionsLPL = new GetCorrectionsListLPL();
        //quizScoresLPL = new GetScoresLPL();
    }

    public String generateParams(String sheet) {
        return GoogleAccess.PARAMNAME_DOC_ID + sheetDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    }

    public String generateParamsPHP(String query) {
        return QuizDatabase.GETDATA_SCRIPT + QuizDatabase.PHP_STARTPARAM +
                QuizDatabase.PARAMNAME_QUERY + query +
                QuizDatabase.PARAMNAME_IDQUIZ + thisQuiz.getListData().getIdQuiz();
    }




    //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...
    public void loadAll() {
        /*
        loadRounds();
        //loadExtradata();
        loadTeams();
        loadOrganizers();
        loadQuestions();
        loadAllAnswers();
        loadAllCorrections();
        //loadScores();
        */
        loadRounds();
        loadQuestions();
        loadTeams();
        loadOrganizers();
        loadAnswers();
        loadAnswersSubmitted();
        loadEventLogs();
    }


    //PHP Loaders
    public void loadRounds(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ROUNDS_FOR_QUIZ);
        getRoundsRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_ROUNDS);
        getRoundsRequest.getItems(new com.paperlessquiz.parsers.RoundParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadQuestions(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_QUESTIONS_FOR_QUIZ);
        getQuestionsRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_QUESTIONS);
        getQuestionsRequest.getItems(new com.paperlessquiz.parsers.QuestionParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadTeams(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_TEAMS_FOR_QUIZ);
        getTeamsRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_TEAMS);
        getTeamsRequest.getItems(new TeamParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadOrganizers(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ORGANIZERS_FOR_QUIZ);
        getOrganizersRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_ORGANIZERS);
        getOrganizersRequest.getItems(new OrganizerParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadAnswers(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERS_FOR_QUIZ);
        getAnswersRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_ANSWERS);
        getAnswersRequest.getItems(new AnswersParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }


    public void loadAnswersSubmitted(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED_FOR_QUIZ);
        getAnswersSubmittedRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_ANSWERSSUBMITTED);
        getAnswersSubmittedRequest.getItems(new AnswersSubmittedParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }


    public void loadEventLogs(){
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_EVENTLOGS_FOR_QUIZ);
        getEventLogsRequest = new HTTPGet<>(context,scriptParams,QuizDatabase.REQUEST_ID_EVENTLOGS);
        getEventLogsRequest.getItems(new LogMessageParser(),new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    /*

    public void loadTeams() {
        //Get the list of participating teams
        String scriptParams = generateParams(QuizGenerator.SHEET_TEAMS);
        GoogleAccessGet<Team> googleAccessGetTeams = new GoogleAccessGet<Team>(context, scriptParams);
        googleAccessGetTeams.getItems(new LoginEntityParser(), quizTeamsLPL,
                new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }

    public boolean teamsOK() {
        //Every  teams id should correspond with its order number in the array
        for (int i = 0; i < quizTeamsLPL.getLoginEntities().size(); i++) {
            if (quizTeamsLPL.getLoginEntities().get(i).getIdUser() != i + 1) {
                return false;
            }
        }
        return true;
    }

    public void loadOrganizers() {
        //Get the list of organizers
        String scriptParams = generateParams(QuizGenerator.SHEET_ORGANIZERS);
        GoogleAccessGet<Team> googleAccessGetOrganizers = new GoogleAccessGet<Team>(context, scriptParams);
        googleAccessGetOrganizers.getItems(new LoginEntityParser(), quizOrganizersLPL,
                new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }

    public boolean organizersOK() {
        //Every  organizers id should correspond with its order number in the array
        for (int i = 0; i < quizOrganizersLPL.getLoginEntities().size(); i++) {
            if (quizOrganizersLPL.getLoginEntities().get(i).getIdUser() != i + 1) {
                return false;
            }
        }
        return true;
    }

    public void loadRounds() {
        //Get the rounds
        String scriptParams = generateParams(QuizGenerator.SHEET_ROUNDS);
        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(context, scriptParams);
        googleAccessGet.getItems(new RoundParser(), quizRoundsLPL,
                new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }

    public void loadQuestions() {
        //Get the list of questions
        String scriptParams = generateParams(QuizGenerator.SHEET_QUESTIONS);
        GoogleAccessGet<Question> googleAccessGetQuestions = new GoogleAccessGet<Question>(context, scriptParams);
        googleAccessGetQuestions.getItems(new QuestionParser(), quizQuestionsLPL,
                new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }


    public boolean allChecksOK() {
        return (teamsOK() && organizersOK() && questionsOK());
    }
    public boolean questionsOK() {
        //There should be round.NrOfQuestions questions for every round
        /*
        if (!(quizQuestionsLPL.getAllQuestionsPerRound().size() == quizExtraDataLPL.getQuizExtraData().getNrOfRounds())) {
            return false;
        } else {
            //Every round should have the correct number of questions
            for (int i = 0; i < quizQuestionsLPL.getAllQuestionsPerRound().size(); i++) {
                if (!(quizRoundsLPL.getRounds().get(i).getNrOfQuestions() == quizQuestionsLPL.getAllQuestionsPerRound().get(i).size())) {
                    return false;
                }
            }
        }
        */
    /*
        return true;
    }

    public void loadAllAnswers() {
        //Get the list of ALL answers per question
        String scriptParams = generateParams(QuizGenerator.SHEET_ANSWERS);
        GoogleAccessGet<AnswersList> googleAccessGetAnswers = new GoogleAccessGet<AnswersList>(context, scriptParams);
        googleAccessGetAnswers.getItems(new AnswersListParser(), quizAnswersLPL,
                new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }

    public void loadAllCorrections() {
        //Get the list of ALL answers per question
        String scriptParams = generateParams(QuizGenerator.SHEET_CORRECTIONS);
        GoogleAccessGet<CorrectionsList> googleAccessGetScores = new GoogleAccessGet<CorrectionsList>(context, scriptParams);
        googleAccessGetScores.getItems(new CorrectionsListParser(), quizCorrectionsLPL,
                new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }
*/

    public void generateBlankAnswers() {
        //for each round
        for (int i = 0; i < quizQuestionsLPL.getAllQuestionsPerRound().size(); i++) {
            myAnswers.add(i, new ArrayList<>());
            //ArrayList<Answer> answers = new ArrayList<>();
            //Create an array with the correct nr of answers
            for (int j = 0; j < quizQuestionsLPL.getAllQuestionsPerRound().get(i).size(); j++) {
                //answers.add(j, new Answer(j, ""));
                myAnswers.get(i).add(j, new Answer(j, ""));
            }
        }

    }
}
