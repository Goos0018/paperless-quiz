package com.paperlessquiz.quiz;

import android.content.Context;
import android.util.EventLog;

import com.paperlessquiz.answer.AnswersSubmitted;
import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.loadinglisteners.LLShowProgressActWhenComplete;
import com.paperlessquiz.loadinglisteners.LLSilent;
import com.paperlessquiz.orders.ItemOrdered;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.parsers.ItemOrderedParser;
import com.paperlessquiz.parsers.OrderItemParser;
import com.paperlessquiz.parsers.OrderParser;
import com.paperlessquiz.users.Organizer;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.users.User;
import com.paperlessquiz.parsers.AnswersParser;
import com.paperlessquiz.parsers.AnswersSubmittedParser;
import com.paperlessquiz.parsers.LogMessageParser;
import com.paperlessquiz.parsers.UserParser;
//import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.paperlessquiz.webrequest.HTTPGetData;
import com.paperlessquiz.webrequest.HTTPSubmit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * This class is used to populates a quiz from the SQL database
 * TODO: split into loader and Getter class
 * Can probably be made abstract
 */

public class QuizLoader {
    private Context context;
    private Quiz thisQuiz = MyApplication.theQuiz;

    public HTTPGetData<User> getUsersRequest;
    public HTTPGetData<Answer> getAnswersRequest;
    public HTTPGetData<Question> getQuestionsRequest;
    public HTTPGetData<Round> getRoundsRequest;
    public HTTPGetData<AnswersSubmitted> getAnswersSubmittedRequest;
    public HTTPGetData<EventLog> getMyEventLogsRequest;
    public HTTPGetData<ResultAfterRound> getResultsRequest;
    public HTTPGetData<OrderItem> getOrderItems;
    public HTTPGetData<Order> getOrders;
    public HTTPGetData<ItemOrdered> getOrderDetails;
    public HTTPSubmit authenticateRequest;
    public HTTPSubmit submitAnswerRequest;
    public HTTPSubmit submitAnswersSubmittedRequest;
    public HTTPSubmit updateRoundStatusRequest;
    public HTTPSubmit updateUserStatusRequest;
    public HTTPSubmit correctQuestionRequest;
    public HTTPSubmit calculateStandingsRequest;
    public HTTPSubmit submitOrderRequest;
    public HTTPSubmit updateOrderStatusRequest;
    public HTTPSubmit updateExistingOrderRequest;

    public QuizLoader(Context context) {
        this.context = context;
    }

    //Generate the most used parameter
    public String generateParamsPHP(String query, int idUser, String userPassword, int idQuiz, int theId) {
        return QuizDatabase.SCRIPT_GET_QUIZDATA + QuizDatabase.PHP_STARTPARAM +
                QuizDatabase.PARAMNAME_QUERY + query + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_IDUSER + idUser + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_USERPASSWORD + userPassword + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_THEIDFORTHEQUERY + theId + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_IDQUIZ + idQuiz;
    }

    //Load the entire quiz
    public void loadQuizFromDb() {
        loadRounds();
        loadQuestions();
        loadMyAnswers();
        loadAnswersSubmitted();
        loadOrderItems();
    }

    public void updateQuizForUser() {
        loadRounds();
        loadMyAnswers();
        loadScoresAndStandings();
    }

    public void loadUsers() {
        String scriptParams = QuizDatabase.SCRIPT_GET_QUIZUSERS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDQUIZ + thisQuiz.getListData().getIdQuiz();
        //generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_TEAMS_FOR_QUIZ);
        getUsersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_USERS);
        getUsersRequest.getItems(new UserParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void loadRounds() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ROUNDS, idUser, userPassword, idQuiz, idQuiz);
        getRoundsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ROUNDS);
        getRoundsRequest.getItems(new com.paperlessquiz.parsers.RoundParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadQuestions() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_QUESTIONS, idUser, userPassword, idQuiz, idQuiz);
        getQuestionsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_QUESTIONS);
        getQuestionsRequest.getItems(new com.paperlessquiz.parsers.QuestionParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void loadFullQuestions() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_FULL_QUESTIONS, idUser, userPassword, idQuiz, idQuiz);
        getQuestionsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_FULLQUESTIONS);
        getQuestionsRequest.getItems(new com.paperlessquiz.parsers.QuestionParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void loadMyAnswers() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_MY_ANSWERS, idUser, userPassword, idQuiz, idUser);
        getAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERS);
        getAnswersRequest.getItems(new AnswersParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadAllAnswers() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERS, idUser, userPassword, idQuiz, idQuiz);
        getAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ALLANSWERS);
        getAnswersRequest.getItems(new AnswersParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadAnswersSubmitted() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED, idUser, userPassword, idQuiz, idQuiz);
        getAnswersSubmittedRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERSSUBMITTED);
        getAnswersSubmittedRequest.getItems(new AnswersSubmittedParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadScoresAndStandings() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_SCORES, idUser, userPassword, idQuiz, idQuiz);
        getResultsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_RESULTS);
        getResultsRequest.getItems(new com.paperlessquiz.parsers.ResultAfterRoundParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadMyEvents() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_MY_EVENTLOGS, idUser, userPassword, idQuiz, idUser);
        getMyEventLogsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_EVENTLOGS);
        getMyEventLogsRequest.getItems(new LogMessageParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadOrderItems() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERITEMS, idUser, userPassword, idQuiz, idQuiz);
        getOrderItems = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERITEMS);
        getOrderItems.getItems(new OrderItemParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    //Load orders for the user
    public void loadOrdersForuser() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERSFORUSER, idUser, userPassword, idQuiz, idUser);
        scriptParams = scriptParams + QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERBY + QuizDatabase.COLNAME_ORDERNUMBER +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERDIRECTION + QuizDatabase.PARAMVALUE_ORDERDESC;
        getOrders = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERSFORUSER);
        getOrders.getItems(new OrderParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    //Load ALL orders with status / category in a given comma separated list
    // This will load all orders if the status resp. category is omitted
    public void loadAllOrders(String statuses, String categories) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String encodedCategories;
        try {
            encodedCategories = URLEncoder.encode(categories.replaceAll("'", "\""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedCategories = "Categories contains illegal characters";
        }
        String scriptParams = QuizDatabase.SCRIPT_GET_ALLORDERS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUIZ+ idQuiz +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERCATEGORIES + encodedCategories +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERSTATUSLIST + statuses ;
    getOrders = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ALLORDERS);
        getOrders.getItems(new OrderParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }

    public void loadOrderDetails(int idOrder) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERDETAILS, idUser, userPassword, idQuiz, idOrder);
        getOrderDetails = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERDETAILS);
        getOrderDetails.getItems(new ItemOrderedParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                "Something went wrong: ", false));
    }


    //Get the items you can order and put them in the MyApplication itemsToOrderArray object
    public void loadItemsToOrderIntoQuiz() {
        for (int i = 0; i < getOrderItems.getResultsList().size(); i++) {
            OrderItem thisItem = getOrderItems.getResultsList().get(i);
            //Add this to the HashMap and the array
            MyApplication.itemsToOrderArray.add(thisItem);
            MyApplication.itemsToOrder.put(Integer.valueOf(thisItem.getIdOrderItem()), thisItem);
        }
    }

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
                    thisQuiz.getTeams().set(thisUser.getUserNr() - 1, new Team(thisUser));
                    break;
                default:
                    int m = 1;
                    while (thisQuiz.getOrganizers().size() < thisUser.getUserNr()) {
                        thisQuiz.getOrganizers().add(new Organizer(m));
                        m++;
                    }
                    thisQuiz.getOrganizers().set(thisUser.getUserNr() - 1, new Organizer(thisUser));
            }
        }
    }

    //Get all the rounds and load them in the quiz
    public void loadRoundsIntoQuiz() {
        for (int i = 0; i < getRoundsRequest.getResultsList().size(); i++) {
            Round thisRound = getRoundsRequest.getResultsList().get(i);
            //Make sure the round is added on the location that corresponds to its roundNr
            int k = 1;
            while (thisQuiz.getRounds().size() < thisRound.getRoundNr()) {
                thisQuiz.getRounds().add(new Round(k));
                k++;
            }
            thisQuiz.getRounds().set(thisRound.getRoundNr() - 1, thisRound);
        }
    }

    //Load all questions into the quiz
    public void loadQuestionsIntoQuiz() {
        for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
            Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
            if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                //TODO: LOG EVENT
            } else {
                Round theRound = thisQuiz.getRound(thisQuestion.getRoundNr());
                //Make sure the question is added to the correct round and on the location that corresponds to its questionNr
                int k = theRound.getQuestions().size();
                while (theRound.getQuestions().size() < thisQuestion.getQuestionNr()) {
                    theRound.getQuestions().add(new Question(k));
                    k++;
                }
                theRound.getQuestions().set(thisQuestion.getQuestionNr() - 1, thisQuestion);
            }
        }
    }

    //Assumes rounds are already loaded in the quiz, this just updates the relevant information
    public void updateRounds() {
        for (int i = 0; i < getRoundsRequest.getResultsList().size(); i++) {
            Round thisRound = getRoundsRequest.getResultsList().get(i);
            //Rounds are already loaded, we can simply get the round from the Quiz object
            Round roundToUpdate = thisQuiz.getRound(thisRound.getRoundNr());
            roundToUpdate.updateRoundBasics(thisRound);
        }
    }

    //Assumes users are already loaded in the quiz, this just updates the relevant information
    public void updateTeams() {
        for (int i = 0; i < getUsersRequest.getResultsList().size(); i++) {
            User thisUser = getUsersRequest.getResultsList().get(i);
            //Users are already loaded, we only care about the teams
            if (thisUser.getUserType() == QuizDatabase.USERTYPE_TEAM) {
                User userToUpdate = thisQuiz.getTeam(thisUser.getUserNr());
                userToUpdate.updateUserBasics(thisUser);
            }
        }
    }

    //When this is run, all questions are already correctly added, we only add the correct answer here for the corrector
    public void updateFullQuestionsIntoQuiz() {
        for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
            Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
            if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                //TODO: LOG EVENT
            } else {
                Round theRound = thisQuiz.getRound(thisQuestion.getRoundNr());
                theRound.getQuestion(thisQuestion.getQuestionNr()).setCorrectAnswer(thisQuestion.getCorrectAnswer());
            }
        }
    }

    //Make sure all answers are correctly initialized before calling this!
    public void updateAnswersIntoQuiz() {
        for (int i = 0; i < getAnswersRequest.getResultsList().size(); i++) {
            Answer theAnswer = getAnswersRequest.getResultsList().get(i);
            thisQuiz.getRound(theAnswer.getRoundNr()).getQuestion(theAnswer.getQuestionNr()).getAllAnswers().set(theAnswer.getTeamNr() - 1, theAnswer);
        }
    }

    public void updateAnswersSubmittedIntoQuiz() {
        for (int i = 0; i < getAnswersSubmittedRequest.getResultsList().size(); i++) {
            AnswersSubmitted thisAnswerSubmitted = getAnswersSubmittedRequest.getResultsList().get(i);
            thisQuiz.getTeam(thisAnswerSubmitted.getTeamNr()).setAnswersForRoundSubmitted(thisAnswerSubmitted.getRoundNr());
        }
    }

    public void loadResultsIntoQuiz() {
        for (int i = 0; i < getResultsRequest.getResultsList().size(); i++) {
            ResultAfterRound thisResult = getResultsRequest.getResultsList().get(i);
            thisQuiz.addResult(thisResult);
        }
    }

    /**
     * Submit activities
     */

    public void authenticateUser(int idUser, String userPassword) {
        String scriptParams = QuizDatabase.SCRIPT_AUTHENTICATE + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword;
        authenticateRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_AUTHENTICATE);
        authenticateRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    public void submitAnswer(int idQuestion, String theAnswer) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String encodedAnswer;
        try {
            encodedAnswer = URLEncoder.encode(theAnswer.replaceAll("'", "\""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedAnswer = "Answer contains illegal characters";
        }
        String scriptParams = QuizDatabase.SCRIPT_SUBMITANSWER + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUESTION + idQuestion +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_THEANSWER + encodedAnswer;
        submitAnswerRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_SUBMITANSWER);
        submitAnswerRequest.sendRequest(new LLSilent());
    }


    public void setAnswersSubmitted(int roundNr) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idRound = thisQuiz.getRound(roundNr).getIdRound();
        String scriptParams = QuizDatabase.SCRIPT_SETANSWERSSUBMITTED + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDROUND + idRound;
        submitAnswersSubmittedRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_SETANSWERSSUBMITTED);
        submitAnswersSubmittedRequest.sendRequest(new LLSilent());
    }

    public void updateRoundStatus(int roundNr, int newStatus) {
        int idRound = thisQuiz.getRound(roundNr).getIdRound();
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_UPDATEROUNDSTATUS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDROUND + idRound +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWROUNDSTATUS + newStatus;
        updateRoundStatusRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATEROUNDSTATUS);
        updateRoundStatusRequest.sendRequest(new LLSilent());
    }

    public void updateMyStatus(int newStatus) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_UPDATEMYSTATUS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWUSERSTATUS + newStatus;
        updateUserStatusRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATEMYSTATUS);
        updateUserStatusRequest.sendRequest(new LLSilent());
    }

    public void updateTeam(int userNr, int newStatus, String newName) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int userToUpdate = thisQuiz.getTeam(userNr).getIdUser();
        String scriptParams = QuizDatabase.SCRIPT_UPDATETEAM + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERTOUPDATE + userToUpdate +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWUSERSTATUS + newStatus +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWUSERNAME + newName;
        updateUserStatusRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATETEAM);
        updateUserStatusRequest.sendRequest(new LLSilent());
    }

    public void correctQuestion(int roundNr, int idQuestion, int isCorrect, String teamIds) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_CORRECTQUESTION + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUESTION + idQuestion +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ISCORRECT + isCorrect +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDTEAMS + teamIds;
        correctQuestionRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_CORRECTQUESTION);
        correctQuestionRequest.sendRequest(new LLSilent());
    }

    public void calculateStandings(int roundNr) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        int idRound = thisQuiz.getRound(roundNr).getIdRound();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_CALCULATESCORESFORROUND + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUIZ + idQuiz +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDROUND + idRound;
        calculateStandingsRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_CALCULATESCORES);
        calculateStandingsRequest.sendRequest(new LLSilent());
    }

    //Create a new order for the user that is logged in
    public void submitOrder(String orderDetails, String time) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        //int idQuiz = thisQuiz.getListData().getIdQuiz();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_SUBMITORDER + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ITEMSTOORDER + orderDetails +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_TIME + time;
        submitOrderRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_SUBMITORDER);
        submitOrderRequest.sendRequest(new LLSilent());
    }

    //Update the order that is passed
    public void updateExistingOrder(Order order, String time) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        //int idQuiz = thisQuiz.getListData().getIdQuiz();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_UPDATEEXISTINGORDER + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ITEMSTOORDER + order.getOrderContentsAsString() +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDORDER + order.getIdOrder() +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_TIME + time;
        updateExistingOrderRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATEEXISTINGORDER);
        updateExistingOrderRequest.sendRequest(new LLSilent());
    }

    public void updateOrderStatus(int idOrder, int newStatus, String time) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_UPDATEORDERSTATUS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDORDER + idOrder +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWORDERSTATUS + newStatus +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_TIME + time;
        updateOrderStatusRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATEORDERSTATUS);
        updateOrderStatusRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

}

