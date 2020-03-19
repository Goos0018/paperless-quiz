package com.paperlessquiz.quiz;

import android.content.Context;
import android.widget.Toast;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.R;
import com.paperlessquiz.loadinglisteners.LLShowProgressActWhenComplete;
import com.paperlessquiz.loadinglisteners.LLSilent;
import com.paperlessquiz.loadinglisteners.LLSilentActWhenComplete;
import com.paperlessquiz.orders.ItemOrdered;
import com.paperlessquiz.orders.Order;
import com.paperlessquiz.orders.OrderItem;
import com.paperlessquiz.parsers.HelpTopicParser;
import com.paperlessquiz.parsers.AnswerStatsParser;
import com.paperlessquiz.parsers.ItemOrderedParser;
import com.paperlessquiz.parsers.OrderItemParser;
import com.paperlessquiz.parsers.OrderParser;
import com.paperlessquiz.parsers.RoundParser;
import com.paperlessquiz.users.Organizer;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.users.User;
import com.paperlessquiz.parsers.AnswersParser;
import com.paperlessquiz.parsers.UserParser;
//import com.example.paperlessquiz.quizextradata.GetQuizExtraDataLPL;
import com.paperlessquiz.webrequest.HTTPGetData;
import com.paperlessquiz.webrequest.HTTPSubmit;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to populate or update a quiz from the SQL database
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
    public HTTPGetData<Integer> getAnswerStatsRequest;
    public HTTPGetData<ResultAfterRound> getResultsRequest;
    public HTTPGetData<OrderItem> getOrderItemsRequest;
    public HTTPGetData<Order> getOrdersRequest;
    public HTTPGetData<ItemOrdered> getOrderDetails;
    public HTTPGetData<HelpTopic> getHelpTopicsRequest;
    public HTTPGetData<HelpTopic> getRemarksRequest;
    public HTTPGetData<HelpTopic> getScoreAfterRoundRequest;

    public HTTPSubmit authenticateRequest;
    public HTTPSubmit submitAnswerRequest;
    public HTTPSubmit updateRoundStatusRequest;
    public HTTPSubmit updateUserStatusRequest;
    public HTTPSubmit correctQuestionRequest;
    public HTTPSubmit setAllAnswersToFalseRequest;
    //public HTTPSubmit calculateStandingsRequest;
    public HTTPSubmit submitOrderRequest;
    public HTTPSubmit updateOrderStatusRequest;
    public HTTPSubmit updateExistingOrderRequest;
    public HTTPSubmit lockOrderForPrepRequest;
    public HTTPSubmit buyBonnekesRequest;
    public HTTPSubmit createPauseEventRequest;
    public HTTPSubmit setSoldOutRequest;
    public HTTPSubmit submitRemarkRequest;
    public HTTPSubmit answerRemarkRequest;
    public HTTPSubmit addUnitsRequest;


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

    //Load details for the user ids given in usersList
    public void loadUsers(String usersList) {
        String scriptParams = QuizDatabase.SCRIPT_GET_QUIZUSERS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDQUIZ + thisQuiz.getListData().getIdQuiz() +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERSLIST + usersList;
        getUsersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_USERS);
        getUsersRequest.getItems(new UserParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load details for all users
    public void loadUsers() {
        loadUsers("");
    }

    //Load details for the given round
    public void loadRounds(int roundNr) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_GET_ROUNDS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ROUNDNR + roundNr;
        getRoundsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ROUNDS);
        getRoundsRequest.getItems(new RoundParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load details for all rounds
    public void loadRounds() {
        int dummy = 0;
        loadRounds(dummy);
    }

    //Load all the questions - except for the answers - for teams
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

    //For the corrector, including the answers
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

    //Load answers for this team and the round given
    public void loadMyAnswers(int roundNr) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_GET_MYANSWERS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ROUNDNR + roundNr;
        getAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERS);
        getAnswersRequest.getItems(new AnswersParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load all answers for this team - not used
    public void loadMyAnswers() {
        int dummy = 0;
        loadMyAnswers(dummy);
    }

    //Again for the corrector - load all answers for all teams
    public void loadAllAnswers() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ALL_ANSWERS, idUser, userPassword, idQuiz, idQuiz);
        getAnswersRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ALLANSWERS);
        getAnswersRequest.getItems(new AnswersParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //For the QuizMaster - load how many (non)empty/corrected answers there are for a round
    //Statistic indicates which types of answers stats we are interested in
    public void loadAnswerStats(int roundNr, int statistic) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = QuizDatabase.SCRIPT_GET_ANSWERSTATS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUIZ + idQuiz +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ROUNDNR + roundNr +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ANSWERSTAT + statistic;
        getAnswerStatsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ANSWERSTATS);
        getAnswerStatsRequest.getItems(new AnswerStatsParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Retrieve the scores after a round for the active user
    public void loadScoresAndStandings(int roundNr) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_GET_SCORES + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ROUNDNR + roundNr;
        getResultsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_SCORES);
        getResultsRequest.getItems(new com.paperlessquiz.parsers.ResultAfterRoundParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    // Load all scores for the active user - not used
    public void loadScoresAndStandings() {
        int dummy = 0;
        loadScoresAndStandings(dummy);
    }

    //Load the items that can be ordered
    public void loadOrderItems() {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERITEMS, idUser, userPassword, idQuiz, idQuiz);
        getOrderItemsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERITEMS);
        getOrderItemsRequest.getItems(new OrderItemParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load ALL orders with status / category / users in a given comma separated list
    //Used by bar responsible, barhelpers and users to retrieve the orders they are interested in
    //If a parameter is blanc, no filtering will be done on that parameter
    public void loadAllOrders(String statuses, String categories, String users) {
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
        getOrdersRequest.getItems(new OrderParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load all orders - not used
    public void loadAllOrders() {
        loadAllOrders("", "", "");
    }

    //Load the details of a specific order
    public void loadOrderDetails(int idOrder) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        int idQuiz = thisQuiz.getListData().getIdQuiz();
        String scriptParams = generateParamsPHP(QuizDatabase.PARAMVALUE_QRY_ORDERDETAILS, idUser, userPassword, idQuiz, idOrder);
        getOrderDetails = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_ORDERDETAILS);
        getOrderDetails.getItems(new ItemOrderedParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load the helptopics for this user type
    public void loadHelpTopics(int helpType) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_GET_HELPTOPICS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPTYPE + helpType;
        getHelpTopicsRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_HELPTOPICS);
        getHelpTopicsRequest.getItems(new HelpTopicParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Load remarks - for bar responsible and juror
    public void loadRemarks(int helpType, boolean loadAllMessages) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_GET_REMARKS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPTYPE + helpType;
        if (!loadAllMessages) {
            scriptParams += QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPNORESPONSEONLY + "1";
        }
        getRemarksRequest = new HTTPGetData<>(context, scriptParams, QuizDatabase.REQUEST_ID_GET_REMARKS);
        getRemarksRequest.getItems(new HelpTopicParser(), new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //METHODS USED TO PUT THE LOADED DATA IN THE QUIZ OBJECT

    //Get the items you can order and put them in the MyApplication itemsToOrderArray object
    public void loadOrderItemsIntoQuiz() {
        MyApplication.itemsToOrderArray = new ArrayList<>();
        MyApplication.itemsToOrder = new HashMap<>();
        for (int i = 0; i < getOrderItemsRequest.getResultsList().size(); i++) {
            OrderItem thisItem = getOrderItemsRequest.getResultsList().get(i);
            //Add this to the HashMap and the array
            MyApplication.itemsToOrderArray.add(thisItem);
            MyApplication.itemsToOrder.put(Integer.valueOf(thisItem.getIdOrderItem()), thisItem);
        }
    }

    //Put all the users into the Teams resp. Organizers arrays of the Quiz object
    public void loadUsersIntoQuiz() {
        try {
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
        } catch (Exception e) {
            Toast.makeText(context, "Error loading users into quiz - please try again", Toast.LENGTH_LONG).show();
        }
    }

    //Put all the rounds in the quiz object
    public void loadRoundsIntoQuiz() {
        try {
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
        } catch (Exception e) {
            Toast.makeText(context, "Error loading rounds into quiz - please try again", Toast.LENGTH_LONG).show();
        }
    }

    //Put all questions into the quiz object
    public void loadQuestionsIntoQuiz() {
        try {
            for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
                Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
                if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                    MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Load Questions error - unexpected roundNr");
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
        } catch (Exception e) {
            Toast.makeText(context, "Error loading questions into quiz - please try again", Toast.LENGTH_LONG).show();
        }
    }

    //Update the rounds of the quiz object
    //Assumes rounds are already loaded in the quiz, this just updates the relevant information
    public boolean updateRoundsIntoQuiz() {
        try {
            for (int i = 0; i < getRoundsRequest.getResultsList().size(); i++) {
                Round thisRound = getRoundsRequest.getResultsList().get(i);
                //Rounds are already loaded, we can simply get the round from the Quiz object
                Round roundToUpdate = thisQuiz.getRound(thisRound.getRoundNr());
                roundToUpdate.updateRoundBasics(thisRound);
            }
            return true;
        } catch (Exception e) {
            MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Error updating rounds into quiz (" + e.toString() + ")");
            return false;
        }
    }

    //Assumes users are already loaded in the quiz, this just updates the relevant information
    public void updateUsersIntoQuiz() {
        try {
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
        } catch (Exception e) {
            MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Error updating users into quiz (" + e.toString() + ")");
        }
    }

    //When this is run, all questions are already correctly added, we only add the correct answer here for the corrector
    public void updateFullQuestionsIntoQuiz() {
        try {
            for (int i = 0; i < getQuestionsRequest.getResultsList().size(); i++) {
                Question thisQuestion = getQuestionsRequest.getResultsList().get(i);
                if (thisQuestion.getRoundNr() > thisQuiz.getRounds().size() + 1) {
                    MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "UpdateFullQuestions error - unexpected roundNr");
                } else {
                    Round theRound = thisQuiz.getRound(thisQuestion.getRoundNr());
                    theRound.getQuestion(thisQuestion.getQuestionNr()).setCorrectAnswer(thisQuestion.getCorrectAnswer());
                }
            }
        } catch (Exception e) {
            MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Error updating full questions into quiz (" + e.toString() + ")");
        }
    }

    //Update answers with correction info - Make sure all answers are correctly initialized before calling this!
    public boolean updateAnswersIntoQuiz() {
        try {
            for (int i = 0; i < getAnswersRequest.getResultsList().size(); i++) {
                Answer theAnswer = getAnswersRequest.getResultsList().get(i);
                thisQuiz.getRound(theAnswer.getRoundNr()).getQuestion(theAnswer.getQuestionNr()).getAllAnswers().set(theAnswer.getTeamNr() - 1, theAnswer);
            }
            return true;
        } catch (Exception e) {
            MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Error updating answers into quiz (" + e.toString() + ")");
            return false;
        }
    }

    //Put the results that were loaded (scores) into the quiz
    public boolean loadResultsIntoQuiz() {
        try {
            for (int i = 0; i < getResultsRequest.getResultsList().size(); i++) {
                ResultAfterRound thisResult = getResultsRequest.getResultsList().get(i);
                thisQuiz.addResult(thisResult);
            }
            return true;
        } catch (Exception e) {
            MyApplication.logMessage(context, QuizDatabase.LOGLEVEL_ERROR, "Error updating results into quiz (" + e.toString() + ")");
            return false;
        }

    }

    /**
     * SUBMIT ACTIVITIES
     */

    //Authenticate a user - php returns appropriate error when password is incorrect
    public void authenticateUser(int idUser, String userPassword) {
        String scriptParams = QuizDatabase.SCRIPT_AUTHENTICATE + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword;
        authenticateRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_AUTHENTICATE);
        authenticateRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Submit an answer. If this was not successful, it will show in the interface by default.
    //Error message will indicate the answer that failed in case another answer was submitted in the mean time
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
        //We supply the idQuestion as request ID because we need to be able to know which question the answer was submitted
        submitAnswerRequest = new HTTPSubmit(context, scriptParams, idQuestion);
        submitAnswerRequest.sendRequest(new LLSilentActWhenComplete(context, context.getString(R.string.quizloader_answernotregistered) + theAnswer));
    }

    //Update the round status - done by the quizmaster
    //19/3/2020: no action done when complete but should not be silent!
    public void updateRoundStatus(int roundNr, int newStatus) {
        int idRound = thisQuiz.getRound(roundNr).getIdRound();
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_UPDATEROUNDSTATUS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDROUND + idRound +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWROUNDSTATUS + newStatus;
        updateRoundStatusRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATEROUNDSTATUS);
        updateRoundStatusRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updateroundstatus),
                context.getString(R.string.loader_errorroundstatusnotupdated) + roundNr, false));
    }

    //Update the status for the user to logged in - silently
    public void updateMyStatus(int newStatus) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_UPDATEMYSTATUS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWUSERSTATUS + newStatus;
        updateUserStatusRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_UPDATEMYSTATUS);
        updateUserStatusRequest.sendRequest(new LLSilent());
    }

    //Update the status for the team given
    //19/3/2020: no action done when complete but should not be silent!
    //TODO: no progress but only error?
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
        updateUserStatusRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updateteamstatus),
                context.getString(R.string.loader_errorteamstatusnotupdated) + userNr, false));
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

    public void setAllAnswersToFalse(int idQuestion, int isCorrect) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_SETALLANSWERSTOFALSE + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDQUESTION + idQuestion +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ISCORRECT + isCorrect;
        setAllAnswersToFalseRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_SETALLANSWERSTOFALSE);
        setAllAnswersToFalseRequest.sendRequest(new LLSilent());
    }

    //8/3/2020: Not needed anymore
    /*
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
    */

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
        //submitOrderRequest.sendRequest(new LLSilent());
        submitOrderRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));

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
        //updateExistingOrderRequest.sendRequest(new LLSilent());
        updateExistingOrderRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
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

    public void lockOrderForPrep(int idOrder, String time) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_LOCKORDERFORPREP + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_IDORDER + idOrder +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_TIME + time;
        lockOrderForPrepRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_LOCKORDERFORPREP);
        lockOrderForPrepRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }

    //Add the amount of bonnekes given to the user's account
    public void buyBonnekes(int userToUpdate, int amount) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_BUYBONNEKES + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERTOUPDATE + userToUpdate +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NROFBONNEKES + amount;
        buyBonnekesRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_BUYBONNEKES);
        buyBonnekesRequest.sendRequest(new LLShowProgressActWhenComplete(context, context.getString(R.string.loader_pleasewait),
                context.getString(R.string.loader_updatingquiz),
                context.getString(R.string.loadingerror), false));
    }


    //Create a pause event for this user
    public void createPauseEvent(int type, long timePaused) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_CREATEPAUSEEVENT + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_PAUSETYPE + type +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_TIMEPAUSED + timePaused;
        createPauseEventRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_CREATEPAUSEEVENT);
        createPauseEventRequest.sendRequest(new LLSilent());
    }

    //Mark an item as sold out (or reset this)
    public void setItemSoldOut(int itemToUpdate, int newItemStatus) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_SETSOLDOUT + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ITEMTOUPDATE + itemToUpdate +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_NEWITEMSTATUS + newItemStatus;
        setSoldOutRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_SETSOLDOUT);
        setSoldOutRequest.sendRequest(new LLSilent());
    }

    //Submit a remark
    public void submitRemark(int helpType, String remark) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String encodedRemark;
        try {
            encodedRemark = URLEncoder.encode(remark.replaceAll("'", "\""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedRemark = "Remark contains characters that are not allowed";
        }
        String scriptParams = QuizDatabase.SCRIPT_SUBMITREMARK + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPTYPE + helpType +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPREMARK + encodedRemark;
        submitRemarkRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_SUBMITREMARK);
        submitRemarkRequest.sendRequest(new LLSilent());
    }

    //Answer a remark
    public void answerRemark(int idRemark, String response) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String encodedResponse;
        try {
            encodedResponse = URLEncoder.encode(response.replaceAll("'", "\""), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            encodedResponse = "Response contains characters that are not allowed";
        }
        String scriptParams = QuizDatabase.SCRIPT_ANSWERREMARK + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPREMARKID + idRemark +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_HELPRESPONSE + encodedResponse;
        answerRemarkRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_ANSWERREMARK);
        answerRemarkRequest.sendRequest(new LLSilent());
    }


    //ncrease the number of available units for an orderitem
    public void addUnits(int itemToUpdate, int extraUnits) {
        int idUser = thisQuiz.getThisUser().getIdUser();
        String userPassword = thisQuiz.getThisUser().getUserPassword();
        String scriptParams = QuizDatabase.SCRIPT_ADDUNITS + QuizDatabase.PHP_STARTPARAM + QuizDatabase.PARAMNAME_IDUSER + idUser +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_USERPASSWORD + userPassword +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_ITEMTOUPDATE + itemToUpdate +
                QuizDatabase.PHP_PARAMCONCATENATOR + QuizDatabase.PARAMNAME_EXTRAUNITS + extraUnits;
        addUnitsRequest = new HTTPSubmit(context, scriptParams, QuizDatabase.REQUEST_ID_ADDUNITS);
        addUnitsRequest.sendRequest(new LLSilent());
    }
}

