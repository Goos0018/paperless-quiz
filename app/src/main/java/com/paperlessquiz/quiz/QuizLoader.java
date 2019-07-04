package com.paperlessquiz.quiz;

import android.content.Context;

import com.example.paperlessquiz.R;
import com.paperlessquiz.corrections.CorrectionsList;
import com.paperlessquiz.corrections.CorrectionsListParser;
import com.paperlessquiz.corrections.GetCorrectionsListLPL;
import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.answer.AnswersList;
import com.paperlessquiz.answer.AnswersListParser;
import com.paperlessquiz.answer.GetAnswersListLPL;
import com.paperlessquiz.googleaccess.GoogleAccess;
import com.paperlessquiz.googleaccess.GoogleAccessGet;
import com.paperlessquiz.googleaccess.LoadingListenerShowProgress;
import com.paperlessquiz.loginentity.GetLoginEntriesLPL;
import com.paperlessquiz.loginentity.LoginEntity;
import com.paperlessquiz.loginentity.LoginEntityParser;
import com.paperlessquiz.question.GetQuestionsLPL;
import com.paperlessquiz.question.Question;
import com.paperlessquiz.question.QuestionParser;
//import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.paperlessquiz.round.GetRoundsLPL;
import com.paperlessquiz.round.Round;
import com.paperlessquiz.round.RoundParser;

import java.util.ArrayList;

//This class populates a quiz from a a Google Sheet
public class QuizLoader {
    private Context context;
    private String sheetDocID;
    //private Quiz quiz;
    //public GetQuizExtraDataLPL quizExtraDataLPL;
    public GetRoundsLPL quizRoundsLPL;
    public GetQuestionsLPL quizQuestionsLPL;
    public GetAnswersListLPL quizAnswersLPL;
    public GetCorrectionsListLPL quizCorrectionsLPL;
    public GetLoginEntriesLPL quizTeamsLPL, quizOrganizersLPL;
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

    //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...
    public void loadAll() {
        loadRounds();
        //loadExtradata();
        loadTeams();
        loadOrganizers();
        loadQuestions();
        loadAllAnswers();
        loadAllCorrections();
        //loadScores();
    }

    public boolean allChecksOK() {
        return (teamsOK() && organizersOK() && questionsOK());
    }


    public void loadTeams() {
        //Get the list of participating teams
        String scriptParams = generateParams(QuizGenerator.SHEET_TEAMS);
        GoogleAccessGet<LoginEntity> googleAccessGetTeams = new GoogleAccessGet<LoginEntity>(context, scriptParams);
        googleAccessGetTeams.getItems(new LoginEntityParser(), quizTeamsLPL,
                new LoadingListenerShowProgress(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
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
        String scriptParams = generateParams(QuizGenerator.SHEET_ORGANIZERS);
        GoogleAccessGet<LoginEntity> googleAccessGetOrganizers = new GoogleAccessGet<LoginEntity>(context, scriptParams);
        googleAccessGetOrganizers.getItems(new LoginEntityParser(), quizOrganizersLPL,
                new LoadingListenerShowProgress(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
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
        String scriptParams = generateParams(QuizGenerator.SHEET_ROUNDS);
        GoogleAccessGet<Round> googleAccessGet = new GoogleAccessGet<Round>(context, scriptParams);
        googleAccessGet.getItems(new RoundParser(), quizRoundsLPL,
                new LoadingListenerShowProgress(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }

    public void loadQuestions() {
        //Get the list of questions
        String scriptParams = generateParams(QuizGenerator.SHEET_QUESTIONS);
        GoogleAccessGet<Question> googleAccessGetQuestions = new GoogleAccessGet<Question>(context, scriptParams);
        googleAccessGetQuestions.getItems(new QuestionParser(), quizQuestionsLPL,
                new LoadingListenerShowProgress(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
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
        return true;
    }

    public void loadAllAnswers() {
        //Get the list of ALL answers per question
        String scriptParams = generateParams(QuizGenerator.SHEET_ANSWERS);
        GoogleAccessGet<AnswersList> googleAccessGetAnswers = new GoogleAccessGet<AnswersList>(context, scriptParams);
        googleAccessGetAnswers.getItems(new AnswersListParser(), quizAnswersLPL,
                new LoadingListenerShowProgress(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
    }

    public void loadAllCorrections() {
        //Get the list of ALL answers per question
        String scriptParams = generateParams(QuizGenerator.SHEET_CORRECTIONS);
        GoogleAccessGet<CorrectionsList> googleAccessGetScores = new GoogleAccessGet<CorrectionsList>(context, scriptParams);
        googleAccessGetScores.getItems(new CorrectionsListParser(), quizCorrectionsLPL,
                new LoadingListenerShowProgress(context, context.getString(R.string.loader_pleasewait), context.getString(R.string.loader_updatingquiz),
                        "Something went wrong: ", false));
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
