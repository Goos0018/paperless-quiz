package com.paperlessquiz.users;

import android.widget.TextView;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.ResultAfterRound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/*
This class represents a user that can log in. Either a participant team or one of the organizers.
The id is the order in which they are listed in the sheet. For teams, this is the team number.
 */
public class Team extends User implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_LOGIN_TYPE = "thisLoginType";
    //int teamNr, teamStatus;
    //private boolean present;
    //private boolean loggedIn;
    //private ArrayList<Boolean> answerForRndsSubmitted;
    private HashMap<Integer, Boolean> answersForRndSubmitted;
    private ArrayList<ResultAfterRound> results;


    public ResultAfterRound getResultAfterRound(int roundNr) {
        return results.get(roundNr - 1);
    }

    public ArrayList<ResultAfterRound> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultAfterRound> results) {
        this.results = results;
    }

    public Team(int idQuiz, int idUser, int userNr, int userType, int userStatus, String name, int totalDeposits) {
        super(idQuiz, idUser, userNr, userType, userStatus, name,totalDeposits);
    }

    public Team(User user) {
        super(user.getIdQuiz(), user.getIdUser(), user.getUserNr(), user.getUserType(), user.getUserStatus(), user.getName(), user.getTotalDeposits());
        answersForRndSubmitted = new HashMap<>();
        results = new ArrayList<>();
        setDescription("Ploeg " + getUserNr());
    }

    //20190728 - Create a dummy team with team nr given
    public Team(int teamNr) {
        super(MyApplication.theQuiz.getListData().getIdQuiz(), 0, teamNr, QuizDatabase.USERTYPE_TEAM, QuizDatabase.USERSTATUS_NOTPRESENT, "EMPTY",0);
    }

    /*
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


        @Override
        public String toString() {
            String tmp;
            tmp = "[\"" + getName() + "\",\"" + getUserPassword() + "\",\"" + Boolean.toString(present) + "\",\"" + Boolean.toString(loggedIn);
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
  public ArrayList<Boolean> getAnswerForRndsSubmitted() {
        return answerForRndsSubmitted;
    }

        public void setAnswerForRndsSubmitted(ArrayList<Boolean> answerForRndsSubmitted) {
        this.answerForRndsSubmitted = answerForRndsSubmitted;
    }

    //This function is used to update existing teams from data that is loaded from the Google sheet
    public void updateTeamBasics(Team team) {
        //setPresent(team.isPresent());
        //setLoggedIn(team.isLoggedIn());
        setAnswerForRndsSubmitted(team.getAnswerForRndsSubmitted());
        setName(team.getName());
    }

        */


    public void setAnswersForRoundSubmitted(int roundNr) {
        answersForRndSubmitted.put(Integer.valueOf(roundNr), Boolean.valueOf(true));
    }

    public boolean isAnswersForThisRoundSubmitted(int roundNr) {
        boolean res;
        try {
            res = answersForRndSubmitted.get(roundNr);
        } catch (Exception e) {
            res = false;
        }
        return res;
    }
}
