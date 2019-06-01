package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
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
        /*
        if (!(quiz == null)) {
            if (loginEntities.get(0).getType().equals(LoginEntity.SELECTION_PARTICIPANT)) {
                quiz.setTeams((ArrayList) list);
            } else {
                quiz.setOrganizers((ArrayList) list);
            }
        }
        */
    }
}
