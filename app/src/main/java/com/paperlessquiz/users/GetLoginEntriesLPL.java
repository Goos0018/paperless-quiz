package com.paperlessquiz.users;

import com.paperlessquiz.googleaccess.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This LPL receives an ArrayList of LoginEntities, we don't need to do anything with this list, just return it
 */
public class GetLoginEntriesLPL implements ListParsedListener<Team> {
    private ArrayList<Team> loginEntities;

    public GetLoginEntriesLPL() {
        this.loginEntities = new ArrayList<Team>();
    }

    public ArrayList<Team> getLoginEntities() {
        return loginEntities;
    }

    public void listParsed(List<Team> list) {
        loginEntities = (ArrayList) list;
        //We want to update MyApplication.theQuiz.getTeam(teamNr), but only if that team already exists and it is a Team
        /*
        if ((loginEntities.get(0).getType().equals(QuizGenerator.SELECTION_PARTICIPANT)) && MyApplication.theQuiz.loadingCompleted) {
            for (int i = 0; i < loginEntities.size(); i++) {
                Team team = list.get(i);
                int teamNr = team.getIdUser();
                if (teamNr <= MyApplication.theQuiz.getTeams().size()) {
                    MyApplication.theQuiz.getTeam(teamNr).updateTeamBasics(team);
                } else {
                }
            }
        }
        */

    }
}
