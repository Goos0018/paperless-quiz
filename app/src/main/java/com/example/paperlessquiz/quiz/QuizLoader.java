package com.example.paperlessquiz.quiz;

import android.content.Context;

import com.example.paperlessquiz.corrections.CorrectionsList;
import com.example.paperlessquiz.corrections.CorrectionsListParser;
import com.example.paperlessquiz.corrections.GetCorrectionsListLPL;
import com.example.paperlessquiz.MyApplication;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.answerslist.AnswersList;
import com.example.paperlessquiz.answerslist.AnswersListParser;
import com.example.paperlessquiz.answerslist.GetAnswersListLPL;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerShowProgress;
import com.example.paperlessquiz.loginentity.GetLoginEntriesLPL;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.loginentity.LoginEntityParser;
import com.example.paperlessquiz.question.GetQuestionsLPL;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.question.QuestionParser;
import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.example.paperlessquiz.quizextradata.QuizExtraData;
import com.example.paperlessquiz.quizextradata.QuizExtraDataParser;
import com.example.paperlessquiz.round.GetRoundsLPL;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.round.RoundParser;
import com.example.paperlessquiz.scores.GetScoresLPL;
import com.example.paperlessquiz.scores.Score;
import com.example.paperlessquiz.scores.ScoreParser;

import java.util.ArrayList;

public class QuizLoader {
    private Context context;
    private String sheetDocID;
    //private Quiz quiz;
    public GetQuizExtraDataLPL quizExtraDataLPL;
    public GetRoundsLPL quizRoundsLPL;
    public GetQuestionsLPL quizQuestionsLPL;
    public GetAnswersListLPL quizAnswersLPL;
    public GetCorrectionsListLPL quizCorrectionsLPL;
    public GetLoginEntriesLPL quizTeamsLPL, quizOrganizersLPL;
    public GetScoresLPL quizScoresLPL;

    public ArrayList<ArrayList<Answer>> myAnswers;

    public QuizLoader(Context context, String sheetDocID) {
        this.context = context;
        this.sheetDocID = sheetDocID;
        myAnswers = new ArrayList<>();
        //this.quiz = MyApplication.theQuiz;
        quizExtraDataLPL = new GetQuizExtraDataLPL();
        quizRoundsLPL = new GetRoundsLPL();
        quizQuestionsLPL = new GetQuestionsLPL();
        quizTeamsLPL = new GetLoginEntriesLPL();
        quizOrganizersLPL = new GetLoginEntriesLPL();
        quizAnswersLPL = new GetAnswersListLPL();
        quizCorrectionsLPL = new GetCorrectionsListLPL();
        quizScoresLPL =new GetScoresLPL();
    }

    public String generateParams(String sheet) {
        return GoogleAccess.PARAMNAME_DOC_ID + sheetDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    }

    //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...
    public void loadAll() {
        loadRounds();
        loadExtradata();
        loadTeams();
        loadOrganizers();
        loadQuestions();
        loadAllAnswers();
        loadAllCorrections();
        loadScores();
    }

    public boolean allChecksOK() {
        return (teamsOK() && organizersOK() && questionsOK());
    }

    //Get extra data for the quiz
    public void loadExtradata() {
        String scriptParams = generateParams(GoogleAccess.SHEET_QUIZDATA);
        GoogleAccessGet<QuizExtraData> googleAccessGetQuizExtraData = new GoogleAccessGet<QuizExtraData>(context, scriptParams, MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetQuizExtraData.getItems(new QuizExtraDataParser(), quizExtraDataLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Loading quiz data",
                        "Something went wrong: ",false));
    }

    public void loadTeams() {
        //Get the list of participating teams
        String scriptParams = generateParams(GoogleAccess.SHEET_TEAMS);
        GoogleAccessGet<LoginEntity> googleAccessGetTeams = new GoogleAccessGet<LoginEntity>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetTeams.getItems(new LoginEntityParser(), quizTeamsLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Updating quiz team info...",
                        "Something went wrong: ",false));
    }

    public boolean teamsOK() {
        //Every  teams id should correspond with its order number in the array
        for (int i = 0; i < quizTeamsLPL.getLoginEntities().size(); i++) {
            if (quizTeamsLPL.getLoginEntities().get(i).getId() != i + 1) {
                return false;
            }
        }
        return true;
    }

    public void loadOrganizers() {
        //Get the list of organizers
        String scriptParams = generateParams(GoogleAccess.SHEET_ORGANIZERS);
        GoogleAccessGet<LoginEntity> googleAccessGetOrganizers = new GoogleAccessGet<LoginEntity>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetOrganizers.getItems(new LoginEntityParser(), quizOrganizersLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Loading quiz organizers...",
                        "Something went wrong: ",false));
    }

    public boolean organizersOK() {
        //Every  organizers id should correspond with its order number in the array
        for (int i = 0; i < quizOrganizersLPL.getLoginEntities().size(); i++) {
            if (quizOrganizersLPL.getLoginEntities().get(i).getId() != i + 1) {
                return false;
            }
        }
        return true;
    }

    public void loadRounds() {
        //Get the rounds
        String scriptParams = generateParams(GoogleAccess.SHEET_ROUNDS);
        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGet.getItems(new RoundParser(), quizRoundsLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Updating rounds information",
                        "Something went wrong: ",false));
    }

    public void loadQuestions() {
        //Get the list of questions
        String scriptParams = generateParams(GoogleAccess.SHEET_QUESTIONS);
        GoogleAccessGet<Question> googleAccessGetQuestions = new GoogleAccessGet<Question>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetQuestions.getItems(new QuestionParser(), quizQuestionsLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Loading questions",
                        "Something went wrong: ",false));
    }

    public boolean questionsOK() {
        //There should be round.NrOfQuestions questions for every round
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
        return true;
    }

    public void loadAllAnswers() {
        //Get the list of ALL answers per question
        String scriptParams = generateParams(GoogleAccess.SHEET_ANSWERS);
        GoogleAccessGet<AnswersList> googleAccessGetAnswers = new GoogleAccessGet<AnswersList>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetAnswers.getItems(new AnswersListParser(), quizAnswersLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Loading other info",
                        "Something went wrong: ",false));
    }

    public void loadAllCorrections() {
        //Get the list of ALL answers per question
        String scriptParams = generateParams(GoogleAccess.SHEET_CORRECTIONS);
        GoogleAccessGet<CorrectionsList> googleAccessGetScores = new GoogleAccessGet<CorrectionsList>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetScores.getItems(new CorrectionsListParser(), quizCorrectionsLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Loading scores",
                        "Something went wrong: ",false));
    }

    public void loadScores() {
        //Get the list of scores per team
        String scriptParams = generateParams(GoogleAccess.SHEET_SCORES);
        GoogleAccessGet<Score> googleAccessGetScores = new GoogleAccessGet<Score>(context, scriptParams,MyApplication.theQuiz.getAdditionalData().getDebugLevel());
        googleAccessGetScores.getItems(new ScoreParser(), quizScoresLPL,
                new LoadingListenerShowProgress(context, "Please wait", "Updating scores...",
                        "Something went wrong: ",false));
    }

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
