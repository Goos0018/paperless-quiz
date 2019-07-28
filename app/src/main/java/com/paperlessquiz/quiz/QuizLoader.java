package com.paperlessquiz.quiz;

import android.content.Context;
import android.util.EventLog;

import com.paperlessquiz.AnswersSubmitted;
import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.googleaccess.GoogleAccess;
import com.paperlessquiz.googleaccess.LLShowProgressActWhenComplete;
import com.paperlessquiz.users.Organizer;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.users.User;
import com.paperlessquiz.parsers.AnswersParser;
import com.paperlessquiz.parsers.AnswersSubmittedParser;
import com.paperlessquiz.parsers.LogMessageParser;
import com.paperlessquiz.parsers.UserParser;
import com.paperlessquiz.question.Question;
//import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.paperlessquiz.round.Round;
import com.paperlessquiz.webrequest.HTTPGetData;
import com.paperlessquiz.webrequest.HTTPSubmit;

import java.util.ArrayList;

//This class is used to populates a quiz from the SQL database
public class QuizLoader {
    private Context context;
    private String sheetDocID;
    private Quiz thisQuiz = MyApplication.theQuiz;
    /*
    //public GetQuizExtraDataLPL quizExtraDataLPL;
    public GetRoundsLPL quizRoundsLPL;
    public GetQuestionsLPL quizQuestionsLPL;
    public GetAnswersListLPL quizAnswersLPL;
    public GetCorrectionsListLPL quizCorrectionsLPL;
    public GetLoginEntriesLPL quizTeamsLPL, quizOrganizersLPL;
    */
    public HTTPGetData<User> getUsersRequest;
    public HTTPGetData<Answer> getMyAnswersRequest;
    public HTTPGetData<Question> getQuestionsRequest;
    public HTTPGetData<Round> getRoundsRequest;
    public HTTPGetData<AnswersSubmitted> getAnswersSubmittedRequest;
    public HTTPGetData<EventLog> getMyEventLogsRequest;
    public HTTPSubmit authenticateRequest;

    //public GetScoresLPL quizScoresLPL;

    public ArrayList<ArrayList<Answer>> myAnswers;

    public QuizLoader(Context context) {
        this.context = context;
        //this.sheetDocID = sheetDocID;
        myAnswers = new ArrayList<>();
        //this.quiz = MyApplication.theQuiz;
        //quizExtraDataLPL = new GetQuizExtraDataLPL();
        /*
        quizRoundsLPL = new GetRoundsLPL();
        quizQuestionsLPL = new GetQuestionsLPL();
        //quizTeamsLPL = new GetLoginEntriesLPL();
        //quizOrganizersLPL = new GetLoginEntriesLPL();
        quizAnswersLPL = new GetAnswersListLPL();
        quizCorrectionsLPL = new GetCorrectionsListLPL();
        //quizScoresLPL = new GetScoresLPL();
        */
    }

    public String generateParams(String sheet) {
        return GoogleAccess.PARAMNAME_DOC_ID + sheetDocID + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + sheet + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
    }

    public String generateParamsPHP(String query, int idUser, String userPassword, int idQuiz) {
        return QuizDatabase.SCRIPT_GET_QUIZDATA + QuizDatabase.PHP_STARTPARAM +
                QuizDatabase.PARAMNAME_QUERY + query + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_IDUSER + idUser + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_USERPASSWORD + userPassword + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_IDQUIZ + idQuiz;
    }


    //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...

    public void loadQuizForuser(int idUser, String userPassword, int idQuiz) {
        loadRounds(idUser, userPassword, idQuiz);
        loadQuestions(idUser, userPassword, idQuiz);
        loadMyAnswers(idUser, userPassword, idQuiz);
        //TODO: loadMyAnswersSubmitted(idUser, userPassword, idQuiz);
        //TODO: loadMyEvents(idUser, userPassword, idQuiz);
        //TODO: updateUserStatuses(idUser, userPassword,  idQuiz);
    }


    public void loadUsers() {
        String scriptParams = QuizDatabase.SCRIPT_GET_QUIZUSERS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDQUIZ + thisQuiz.getListData().getIdQuiz();
        //generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_TEAMS_FOR_QUIZ);
        getUsersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_USERS);
        getUsersRequest.getItems(new UserParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void authenticateUser(int idUser, String userPassword) {
        String scriptParams = QuizDatabase.SCRIPT_AUTHENTICATE + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword;
        authenticateRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_AUTHENTICATE);
        authenticateRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void loadRounds(int idUser, String userPassword, int idQuiz) {
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ROUNDS, idUser, userPassword, idQuiz);
        getRoundsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ROUNDS);
        getRoundsRequest.getItems(new com.paperlessquiz.parsers.RoundParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadQuestions(int idUser, String userPassword, int idQuiz) {
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_QUESTIONS, idUser, userPassword, idQuiz);
        getQuestionsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_QUESTIONS);
        getQuestionsRequest.getItems(new com.paperlessquiz.parsers.QuestionParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void loadMyAnswers(int idUser, String userPassword, int idQuiz) {
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_MY_ANSWERS, idUser, userPassword, idQuiz);
        getMyAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERS);
        getMyAnswersRequest.getItems(new AnswersParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadMyAnswersSubmitted(int idUser, String userPassword, int idQuiz) {
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED, idUser, userPassword, idQuiz);
        getAnswersSubmittedRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERSSUBMITTED);
        getAnswersSubmittedRequest.getItems(new AnswersSubmittedParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }


    public void loadMyEvents(int idUser, String userPassword, int idQuiz) {
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_MY_EVENTLOGS, idUser, userPassword, idQuiz);
        getMyEventLogsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_EVENTLOGS);
        getMyEventLogsRequest.getItems(new LogMessageParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    /*
    public void generateBlankAnswers() {
        //for each round
        for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
            myAnswers.add(i, new ArrayList<>());
            //ArrayList<Answer> answers = new ArrayList<>();
            //Create an array with the correct nr of answers
            for (int j = 0; j < getQuestionsRequest.getResultsList().size(); j++) {
                //answers.add(j, new Answer(j, ""));
                myAnswers.get(i).add(j, new Answer(j, ""));
            }
        }

    }
    */

    //Get all the users and put them into the Teams resp. Organizers array of the Quiz
    public void loadUsersIntoQuiz() {
        for (int i = 0; i < getUsersRequest.getResultsList().size(); i++) {
            User thisUser = getUsersRequest.getResultsList().get(i);
            //Make sure the user is added on the location that corresponds to its userNr
            switch (thisUser.getUserType()) {
                case QuizDatabase.USERTYPE_TEAM:
                    int k = 1;
                    while (thisQuiz.getTeams().size() < thisUser.getUserNr()) {
                        thisQuiz.getTeams().add(new Team(k));
                        k++;
                    }
                    thisQuiz.getTeams().set(thisUser.getUserNr() - 1,new Team(thisUser));
                    break;
                default:
                    int m = 1;
                    while (thisQuiz.getOrganizers().size() < thisUser.getUserNr()) {
                        thisQuiz.getOrganizers().add(new Organizer(m));
                        m++;
                    }
                    thisQuiz.getOrganizers().set(thisUser.getUserNr() - 1,new Organizer(thisUser));
            }
        }
    }

    public void loadRoundsIntoQuiz() {
        for (int i = 0; i < getRoundsRequest.getResultsList().size(); i++) {
            Round thisRound = getRoundsRequest.getResultsList().get(i);
            //Make sure the round is added on the location that corresponds to its roundNr
            int k=1;
            while (thisQuiz.getRounds().size() < thisRound.getRoundNr()) {
                thisQuiz.getRounds().add(new Round(k));
                k++;
            }
            thisQuiz.getRounds().set(thisRound.getRoundNr()-1, thisRound);
        }

    }

    public void loadQuestionsIntoQuiz() {
        for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
            Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
            if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                //TODO: LOG EVENT
            } else {
                Round theRound = thisQuiz.getRound(thisQuestion.getRoundNr());
                //Make sure the question is added to the correct round and on the location that corresponds to its questionNr
                int k=theRound.getQuestions().size();
                while (theRound.getQuestions().size() < thisQuestion.getQuestionNr()) {
                    theRound.getQuestions().add(new Question(k));
                    k++;
                }
                theRound.getQuestions().set(thisQuestion.getQuestionNr()-1,thisQuestion);
            }
        }
    }

    public void loadMyAnswersIntoQuiz(){
        //Make sure we have answers for all teams and all questions
        thisQuiz.initializeAllAnswers();
        for (int i = 0; i < getMyAnswersRequest.getResultsList().size(); i++) {
            Answer theAnswer = getMyAnswersRequest.getResultsList().get(i);
            thisQuiz.getRound(theAnswer.getRoundNr()).getQuestion(theAnswer.getQuestionNr()).getAllAnswers().set(theAnswer.getTeamNr() - 1,theAnswer);
        }

    }
}
