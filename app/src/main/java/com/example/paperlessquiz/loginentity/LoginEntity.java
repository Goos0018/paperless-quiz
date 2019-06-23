package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.quiz.QuizGenerator;
import com.example.paperlessquiz.quiz.ResultAfterRound;

import java.io.Serializable;
import java.util.ArrayList;

/*
This class represents a user that can log in. Either a participant team or one of the organizers.
The id is the order in which they are listed in the sheet. For teams, this is the team number.
 */
public class LoginEntity implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_LOGIN_TYPE = "thisLoginType";


    private int id;
    private String name;
    private String passkey;
    private String type;
    private boolean present;
    private boolean loggedIn;
    private ArrayList<Boolean> answerForRndsSubmitted;
    private ArrayList<ResultAfterRound> results;

    public LoginEntity(int id, String type, String name, String passkey) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.passkey = passkey;
        results =new ArrayList<>();
    }


    public ResultAfterRound getResultAfterRound(int roundNr){
        return results.get(roundNr - 1);
    }

    public ArrayList<ResultAfterRound> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultAfterRound> results) {
        this.results = results;
    }

    public String getName() {
        return name;
    }

    public String getPasskey() {
        return passkey;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public ArrayList<Boolean> getAnswerForRndsSubmitted() {
        return answerForRndsSubmitted;
    }

    public void setAnswerForRndsSubmitted(ArrayList<Boolean> answerForRndsSubmitted) {
        this.answerForRndsSubmitted = answerForRndsSubmitted;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAnswersForThisRoundSubmitted(int roundNr) {
        return this.answerForRndsSubmitted.get(roundNr - 1).booleanValue();
    }

    public void updateTeamBasics(LoginEntity team) {
        if (team.getType().equals(QuizGenerator.SELECTION_PARTICIPANT)) {
            setPresent(team.isPresent());
            setLoggedIn(team.isLoggedIn());
            setAnswerForRndsSubmitted(team.getAnswerForRndsSubmitted());
        }
    }

    @Override
    public String toString() {
        String tmp;
        if (type.equals(QuizGenerator.TYPE_PARTICIPANT)){
        tmp = "[\"" + name + "\",\"" + passkey + "\",\"" + Boolean.toString(present) + "\",\"" + Boolean.toString(loggedIn);
        for (int i = 0; i < answerForRndsSubmitted.size(); i++) {
            tmp = tmp + "\",\"" + Boolean.toString(answerForRndsSubmitted.get(i));
        }
        tmp = tmp + "\"]";}
        else {tmp = "";}
        return tmp;
    }
}
