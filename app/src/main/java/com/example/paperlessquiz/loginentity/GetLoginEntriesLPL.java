package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetLoginEntriesLPL implements ListParsedListener<LoginEntity> {

    //private ParticipantsAdapter participantsAdapter;
    private ArrayList<LoginEntity> loginEntities;

    //public GetLoginEntriesLPL(ParticipantsAdapter participantsAdapter) {
    public GetLoginEntriesLPL() {
        //this.participantsAdapter = participantsAdapter;
        this.loginEntities = new ArrayList<LoginEntity>();
    }

    public ArrayList<LoginEntity> getLoginEntities() {
        return loginEntities;
    }

    public void listParsed(List<LoginEntity> list) {
        loginEntities = (ArrayList)list;
    }

    //@Override
    public List<LoginEntity> getData() {
        return null;
    }
}
