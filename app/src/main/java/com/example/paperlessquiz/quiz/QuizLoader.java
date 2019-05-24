package com.example.paperlessquiz.quiz;

import android.content.Context;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.answerslist.AnswersList;
import com.example.paperlessquiz.answerslist.AnswersListParser;
import com.example.paperlessquiz.answerslist.GetAnswersListLPL;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
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

import java.util.ArrayList;

public class QuizLoader {
    private Context context;
    private String sheetDocID;
    private Quiz quiz;
    public GetQuizExtraDataLPL quizExtraDataLPL = new GetQuizExtraDataLPL();
    public GetRoundsLPL quizRoundsLPL;
    public GetQuestionsLPL quizQuestionsLPL;
    public GetAnswersListLPL quizAnswersLPL;
    public GetLoginEntriesLPL quizTeamsLPL, quizOrganizersLPL;
    public ArrayList<ArrayList<Answer>> myAnswers;

    public QuizLoader(Context context, String sheetDocID, Quiz quiz) {
        this.context = context;
        this.sheetDocID = sheetDocID;
        myAnswers = new ArrayList<>();
        this.quiz = quiz;
        quizExtraDataLPL = new GetQuizExtraDataLPL();
        quizRoundsLPL = new GetRoundsLPL(quiz);
        quizQuestionsLPL = new GetQuestionsLPL();
        quizTeamsLPL = new GetLoginEntriesLPL(quiz);
        quizOrganizersLPL = new GetLoginEntriesLPL();
        quizAnswersLPL = new GetAnswersListLPL(quiz);
        //Add quiz argument as argument for all constructors/LPL's

    }

    public String generateParams(String sheet) {
        return GoogleAccess.PARAMNAME_DOC_ID + sheetDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    }

    //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...
    public void loadAll() {
        loadExtradata();
        loadTeams();
        loadOrganizers();
        loadRounds();
        loadQuestions();
        loadAllAnswers();
    }

    public boolean allChecksOK() {
        return (teamsOK() && organizersOK() && questionsOK());
    }

    //Get extra data from the quiz
    public void loadExtradata() {
        String scriptParams = generateParams(GoogleAccess.SHEET_QUIZDATA);
        GoogleAccessGet<QuizExtraData> googleAccessGetQuizExtraData = new GoogleAccessGet<QuizExtraData>(context, scriptParams);
        googleAccessGetQuizExtraData.getItems(new QuizExtraDataParser(), quizExtraDataLPL,
                new LoadingListenerImpl(context, "Please wait", "Loading quiz data", "Something went wrong: "));
    }

    public void loadTeams() {
        //Get the list of participating teams
        String scriptParams = generateParams(GoogleAccess.SHEET_TEAMS);
        GoogleAccessGet<LoginEntity> googleAccessGetTeams = new GoogleAccessGet<LoginEntity>(context, scriptParams);
        googleAccessGetTeams.getItems(new LoginEntityParser(), quizTeamsLPL,
                new LoadingListenerImpl(context, "Please wait", "Loading quiz participants...", "Something went wrong: "));
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
        GoogleAccessGet<LoginEntity> googleAccessGetOrganizers = new GoogleAccessGet<LoginEntity>(context, scriptParams);
        googleAccessGetOrganizers.getItems(new LoginEntityParser(), quizOrganizersLPL,
                new LoadingListenerImpl(context, "Please wait", "Loading quiz organizers...", "Something went wrong: "));
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
        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(context, scriptParams);
        googleAccessGet.getItems(new RoundParser(), quizRoundsLPL,
                new LoadingListenerImpl(context, "Please wait", "Loading rounds", "Something went wrong: "));
    }

    public void loadQuestions() {
        //Get the list of questions
        String scriptParams = generateParams(GoogleAccess.SHEET_QUESTIONS);
        GoogleAccessGet<Question> googleAccessGetQuestions = new GoogleAccessGet<Question>(context, scriptParams);
        googleAccessGetQuestions.getItems(new QuestionParser(), quizQuestionsLPL,
                new LoadingListenerImpl(context, "Please wait", "Loading questions", "Something went wrong: "));
    }

    public boolean questionsOK() {
        //There should be questions for every round
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
        //Get the list of questions
        String scriptParams = generateParams(GoogleAccess.SHEET_ANSWERS);
        GoogleAccessGet<AnswersList> googleAccessGetAnswers = new GoogleAccessGet<AnswersList>(context, scriptParams);
        googleAccessGetAnswers.getItems(new AnswersListParser(), quizAnswersLPL,
                new LoadingListenerImpl(context, "Please wait", "Loading answers", "Something went wrong: "));
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
