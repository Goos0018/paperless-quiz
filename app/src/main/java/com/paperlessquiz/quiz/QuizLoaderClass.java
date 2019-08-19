package com.paperlessquiz.quiz;

import android.content.Context;
import android.util.EventLog;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.answer.AnswersSubmitted;
import com.paperlessquiz.loadinglisteners.LLShowProgressActWhenComplete;
import com.paperlessquiz.loadinglisteners.LoadingListener;
import com.paperlessquiz.orders.ItemOrdered;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.parsers.AnswersParser;
import com.paperlessquiz.parsers.AnswersSubmittedParser;
import com.paperlessquiz.parsers.ItemOrderedParser;
import com.paperlessquiz.parsers.LogMessageParser;
import com.paperlessquiz.parsers.OrderItemParser;
import com.paperlessquiz.parsers.OrderParser;
import com.paperlessquiz.parsers.UserParser;
import com.paperlessquiz.users.Organizer;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.users.User;
import com.paperlessquiz.webrequest.HTTPGetData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This abstract class contains all HTTPGet requests to get data to load into the quiz + the methods to load the retrieved data into the quiz.
 * Each method uses its own HTTPGetRequest object. Loading listeners are passed by the calling activity
 * NOT USED YET
 */

public abstract class QuizLoaderClass {
/*
    public static Quiz thisQuiz = MyApplication.theQuiz;
    public static HTTPGetData<User> getUsersRequest;
    public static HTTPGetData<Answer> getAnswersRequest;
    public static HTTPGetData<Question> getQuestionsRequest;
    public static HTTPGetData<Round> getRoundsRequest;
    public static HTTPGetData<AnswersSubmitted> getAnswersSubmittedRequest;
    public static HTTPGetData<EventLog> getMyEventLogsRequest;
    public static HTTPGetData<ResultAfterRound> getResultsRequest;
    public static HTTPGetData<OrderItem> getOrderItemsRequest;
    public static HTTPGetData<Order> getOrdersRequest;
    public static HTTPGetData<ItemOrdered> getOrderDetails;

    //Generate the most used parameter strings
    private static String generateParamsPHP(String query, int idUser, String userPassword, int idQuiz, int theId) {
        return QuizDatabase.SCRIPT_GET_QUIZDATA + QuizDatabase.PHP_STARTPARAM +
                QuizDatabase.PARAMNAME_QUERY + query + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_IDUSER + idUser + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_USERPASSWORD + userPassword + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_THEIDFORTHEQUERY + theId + QuizDatabase.PHP_PARAMCONCATENATOR +
                QuizDatabase.PARAMNAME_IDQUIZ + idQuiz;
    }


    public static void loadUsers(Context context, LoadingListener loadingListener, String usersList) {
        String scriptParams = QuizDatabase.SCRIPT_GET_QUIZUSERS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDQUIZ + thisQuiz.getListData().getIdQuiz() +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERSLIST + usersList;
        getUsersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_USERS);
        getUsersRequest.getItems(new UserParser(), loadingListener);
    }

    public static void loadUsers(Context context, LoadingListener loadingListener) {
        loadUsers(context, loadingListener, "");
    }

    public static void loadRounds(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ROUNDS, idUser, userPassword, idQuiz, idQuiz);
        getRoundsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ROUNDS);
        getRoundsRequest.getItems(new com.paperlessquiz.parsers.RoundParser(), loadingListener);
    }

    public static void loadQuestions(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_QUESTIONS, idUser, userPassword, idQuiz, idQuiz);
        getQuestionsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_QUESTIONS);
        getQuestionsRequest.getItems(new com.paperlessquiz.parsers.QuestionParser(), loadingListener);
    }

    public static void loadFullQuestions(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_FULL_QUESTIONS, idUser, userPassword, idQuiz, idQuiz);
        getQuestionsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_FULLQUESTIONS);
        getQuestionsRequest.getItems(new com.paperlessquiz.parsers.QuestionParser(), loadingListener);
    }

    public static void loadMyAnswers(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_MY_ANSWERS, idUser, userPassword, idQuiz, idUser);
        getAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERS);
        getAnswersRequest.getItems(new AnswersParser(), loadingListener);
    }

    public static void loadAllAnswers(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERS, idUser, userPassword, idQuiz, idQuiz);
        getAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ALLANSWERS);
        getAnswersRequest.getItems(new AnswersParser(), loadingListener);
    }

    //Not needed anymore?
    public static void loadAnswersSubmitted(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERSSUBMITTED, idUser, userPassword, idQuiz, idQuiz);
        getAnswersSubmittedRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERSSUBMITTED);
        getAnswersSubmittedRequest.getItems(new AnswersSubmittedParser(), loadingListener);
    }

    public static void loadScoresAndStandings(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_SCORES, idUser, userPassword, idQuiz, idQuiz);
        getResultsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_RESULTS);
        getResultsRequest.getItems(new com.paperlessquiz.parsers.ResultAfterRoundParser(), loadingListener);
    }

    //Not used yet
    public static void loadMyEvents(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_MY_EVENTLOGS, idUser, userPassword, idQuiz, idUser);
        getMyEventLogsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_EVENTLOGS);
        getMyEventLogsRequest.getItems(new LogMessageParser(), loadingListener);
    }

    public static void loadOrderItems(Context context, LoadingListener loadingListener) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERITEMS, idUser, userPassword, idQuiz, idQuiz);
        getOrderItemsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERITEMS);
        getOrderItemsRequest.getItems(new OrderItemParser(), loadingListener);
    }

    //Load ALL orders with status / category / users in a given comma separated list
    // This will load all orders if the status resp. category resp. users is omitted
    public static void loadAllOrders(Context context, LoadingListener loadingListener, String statuses, String categories, String users) {
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
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUIZ + idQuiz +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERCATEGORIES + encodedCategories +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERSTATUSLIST + statuses +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ORDERUSERSLIST + users;
        getOrdersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ALLORDERS);
        getOrdersRequest.getItems(new OrderParser(), loadingListener);
    }

    public static void loadAllOrders(Context context, LoadingListener loadingListener) {
        loadAllOrders(context,loadingListener,"", "", "");
    }

    public static void loadOrderDetails(Context context, LoadingListener loadingListener,int idOrder) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERDETAILS, idUser, userPassword, idQuiz, idOrder);
        getOrderDetails = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERDETAILS);
        getOrderDetails.getItems(new ItemOrderedParser(), loadingListener);
    }


    //
    //Get all the users and put them into the Teams resp. Organizers array of the Quiz
    //Make sure users are loaded ordered by usertype and then by usernr
    public static void loadUsersIntoQuiz() {
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

    //Get the items you can order and put them in the MyApplication itemsToOrderArray and the corresponding hashmap object
    public static void loadOrderItemsIntoQuiz() {
        MyApplication.itemsToOrderArray = new ArrayList<>();
        MyApplication.itemsToOrder = new HashMap<>();
        for (int i = 0; i < getOrderItemsRequest.getResultsList().size(); i++) {
            OrderItem thisItem = getOrderItemsRequest.getResultsList().get(i);
            //Add this to the HashMap and the array
            MyApplication.itemsToOrderArray.add(thisItem);
            MyApplication.itemsToOrder.put(Integer.valueOf(thisItem.getIdOrderItem()), thisItem);
        }
    }

    //Get all the rounds and load them in the quiz
    public static void loadRoundsIntoQuiz() {
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
    //Make sure questions are retrieved ordered by Round Nr and then by Question nr, this is the most efficient
    public static void loadQuestionsIntoQuiz() {
        for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
            Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
            if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                //Dismiss this question, this should never happen
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

    //Assumes rounds are already loaded in the quiz, this just updates the relevant information (mainly status)
    public static void updateRoundsIntoQuiz() {
        for (int i = 0; i < getRoundsRequest.getResultsList().size(); i++) {
            Round thisRound = getRoundsRequest.getResultsList().get(i);
            //Rounds are already loaded, we can simply get the round from the Quiz object
            Round roundToUpdate = thisQuiz.getRound(thisRound.getRoundNr());
            roundToUpdate.updateRoundBasics(thisRound);
        }
    }

    //Assumes users are already loaded in the quiz, this just updates the relevant information
    public static void updateUsersIntoQuiz() {
        for (int i = 0; i < getUsersRequest.getResultsList().size(); i++) {
            User thisUser = getUsersRequest.getResultsList().get(i);
            //Users are already loaded, we only care about the teams
            if (thisUser.getUserType() == QuizDatabase.USERTYPE_TEAM) {
                User userToUpdate = thisQuiz.getTeam(thisUser.getUserNr());
                userToUpdate.updateUserBasics(thisUser);
            } else {
                User userToUpdate = thisQuiz.getOrganizer(thisUser.getUserNr());
                userToUpdate.updateUserBasics(thisUser);
            }
        }
    }

    //When this is run, all questions are already correctly added, we only add the correct answer here for the corrector
    public static void updateFullQuestionsIntoQuiz() {
        for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
            Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
            if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                //Dismiss this question, this should never happen
            } else {
                Round theRound = thisQuiz.getRound(thisQuestion.getRoundNr());
                theRound.getQuestion(thisQuestion.getQuestionNr()).setCorrectAnswer(thisQuestion.getCorrectAnswer());
            }
        }
    }

    //Make sure all answers are correctly initialized before calling this!
    public static void updateAnswersIntoQuiz() {
        for (int i = 0; i < getAnswersRequest.getResultsList().size(); i++) {
            Answer theAnswer = getAnswersRequest.getResultsList().get(i);
            thisQuiz.getRound(theAnswer.getRoundNr()).getQuestion(theAnswer.getQuestionNr()).getAllAnswers().set(theAnswer.getTeamNr() - 1, theAnswer);
        }
    }

    public static void updateAnswersSubmittedIntoQuiz() {
        for (int i = 0; i < getAnswersSubmittedRequest.getResultsList().size(); i++) {
            AnswersSubmitted thisAnswerSubmitted = getAnswersSubmittedRequest.getResultsList().get(i);
            thisQuiz.getTeam(thisAnswerSubmitted.getTeamNr()).setAnswersForRoundSubmitted(thisAnswerSubmitted.getRoundNr());
        }
    }

    public static void loadResultsIntoQuiz() {
        for (int i = 0; i < getResultsRequest.getResultsList().size(); i++) {
            ResultAfterRound thisResult = getResultsRequest.getResultsList().get(i);
            thisQuiz.addResult(thisResult);
        }
    }

    */
}
