package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.HashMap;
import java.util.List;

public class AddLoginEntitiesToParticipantsAdapterLPL implements ListParsedListener<LoginEntity> {

    //private ParticipantsAdapter participantsAdapter;
    private HashMap<String, LoginEntity> loginEntities;

    //public AddLoginEntitiesToParticipantsAdapterLPL(ParticipantsAdapter participantsAdapter) {
    public AddLoginEntitiesToParticipantsAdapterLPL() {
        //this.participantsAdapter = participantsAdapter;
        this.loginEntities = new HashMap<String, LoginEntity>();
    }

    public HashMap<String, LoginEntity> getLoginEntities() {
        return loginEntities;
    }

    public void listParsed(List<LoginEntity> list) {
        //participantsAdapter.addAll(list);
        for (LoginEntity entry : list) {
            loginEntities.put(entry.getId(), entry);
        }
    }

}
