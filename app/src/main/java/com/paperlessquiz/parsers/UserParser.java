package com.paperlessquiz.parsers;

import com.paperlessquiz.users.User;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class UserParser implements JsonParser<User> {

    @Override
    public User parse(JSONObject jo) throws JSONException {
        int idQuiz, idUser, userNr, userStatus,userType, totalDeposit;
        String userName;
        User user;
        try {
            idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            idUser = jo.getInt(QuizDatabase.COLNAME_ID_USER);
            userNr = jo.getInt(QuizDatabase.COLNAME_USER_NR);
            userStatus = jo.getInt(QuizDatabase.COLNAME_USER_STATUS);
            userType = jo.getInt(QuizDatabase.COLNAME_USER_TYPE);
            userName = jo.getString(QuizDatabase.COLNAME_USER_NAME);
            try {totalDeposit=jo.getInt(QuizDatabase.COLNAME_USER_DEPOSIT);}
            catch (Exception e){
                totalDeposit=0;
            }
        } catch (Exception e) {
            idQuiz = 0;
            idUser = 0;
            userNr =0;
            userStatus =0;
            userType=0;
            //teamPassword="";
            userName = "Error parsing " + jo.toString();
            totalDeposit=0;
        }
        user = new User(idQuiz, idUser, userNr, userType, userStatus, userName,totalDeposit);
        return user;
    }
}
