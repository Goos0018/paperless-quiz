package com.paperlessquiz.loginentity;

import com.paperlessquiz.quiz.QuizGenerator;
import com.paperlessquiz.quiz.ResultAfterRound;

import java.io.Serializable;
import java.util.ArrayList;

/*
This class represents a user that can log in. Either a participant team or one of the organizers.
The id is the order in which they are listed in the sheet. For teams, this is the team number.
 */
public class Team extends User implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_LOGIN_TYPE = "thisLoginType";
    int teamNr, teamStatus;
    private boolean present;
    private boolean loggedIn;
    private ArrayList<Boolean> answerForRndsSubmitted;
    private ArrayList<ResultAfterRound> results;

    public Team(int idQuiz, int idUser, String name, String passkey, int teamNr, int teamStatus) {
        super(idQuiz, idUser, name, passkey);
        this.teamNr = teamNr;
        this.teamStatus = teamStatus;
        setDescription("Ploeg " + teamNr);
        present=false;
        loggedIn=false;
    }

    public ResultAfterRound getResultAfterRound(int roundNr) {
        return results.get(roundNr - 1);
    }

    public ArrayList<ResultAfterRound> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultAfterRound> results) {
        this.results = results;
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

    public boolean isAnswersForThisRoundSubmitted(int roundNr) {
        return this.answerForRndsSubmitted.get(roundNr - 1).booleanValue();
    }

    //This function is used to update existing teams from data that is loaded from the Google sheet
    public void updateTeamBasics(Team team) {
        setPresent(team.isPresent());
        setLoggedIn(team.isLoggedIn());
        setAnswerForRndsSubmitted(team.getAnswerForRndsSubmitted());
        setName(team.getName());
    }

    @Override
    public String toString() {
        String tmp;
        tmp = "[\"" + getName() + "\",\"" + getPasskey() + "\",\"" + Boolean.toString(present) + "\",\"" + Boolean.toString(loggedIn);
        for (int i = 0; i < answerForRndsSubmitted.size(); i++) {
            tmp = tmp + "\",\"" + Boolean.toString(answerForRndsSubmitted.get(i));
        }
        tmp = tmp + "\"]";
        return tmp;
    }

    public int getTeamNr() {
        return teamNr;
    }

    public int getTeamStatus() {
        return teamStatus;
    }

    public void setTeamStatus(int teamStatus) {
        this.teamStatus = teamStatus;
    }
}
