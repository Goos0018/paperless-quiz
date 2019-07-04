package com.paperlessquiz.loginentity;

import com.paperlessquiz.MyApplication;
import com.paperlessquiz.googleaccess.ListParsedListener;
import com.paperlessquiz.quiz.QuizGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * This LPL receives an ArrayList of LoginEntities, we don't need to do anything with this list, just return it
 */
public class GetLoginEntriesLPL implements ListParsedListener<LoginEntity> {
    private ArrayList<LoginEntity> loginEntities;

    public GetLoginEntriesLPL() {
        this.loginEntities = new ArrayList<LoginEntity>();
    }

    public ArrayList<LoginEntity> getLoginEntities() {
        return loginEntities;
    }

    public void listParsed(List<LoginEntity> list) {
        loginEntities = (ArrayList) list;
        //We want to update MyApplication.theQuiz.getTeam(teamNr), but only if that team already exists and it is a Team
        if ((loginEntities.get(0).getType().equals(QuizGenerator.SELECTION_PARTICIPANT)) && MyApplication.theQuiz.loadingCompleted) {
            for (int i = 0; i < loginEntities.size(); i++) {
                LoginEntity team = list.get(i);
                int teamNr = team.getId();
                if (teamNr <= MyApplication.theQuiz.getTeams().size()) {
                    MyApplication.theQuiz.getTeam(teamNr).updateTeamBasics(team);
                } else {
                }
            }
        }

    }
}
