package com.paperlessquiz.parsers;

import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.quiz.QuizDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class TeamParser implements JsonParser<Team> {

    @Override
    public Team parse(JSONObject jo) throws JSONException {
        int idQuiz, idTeam, teamNr, teamStatus;
        String teamName, teamPassword;
        Team team;
        try {
            idQuiz = jo.getInt(QuizDatabase.COLNAME_ID_QUIZ);
            idTeam = jo.getInt(QuizDatabase.COLNAME_ID_TEAM);
            teamNr = jo.getInt(QuizDatabase.COLNAME_TEAM_NR);
            teamStatus = jo.getInt(QuizDatabase.COLNAME_TEAM_STATUS);
            teamPassword = jo.getString(QuizDatabase.COLNAME_TEAM_PASSWORD);
            teamName = jo.getString(QuizDatabase.COLNAME_TEAM_NAME);
        } catch (Exception e) {
            idQuiz = 0;
            idTeam = 0;
            teamNr=0;
            teamStatus=0;
            teamPassword="";
            teamName = "Error parsing " + jo.toString();
        }
        team = new Team(idQuiz, idTeam,teamName,teamPassword,teamNr, teamStatus);
        return team;
    }
}
