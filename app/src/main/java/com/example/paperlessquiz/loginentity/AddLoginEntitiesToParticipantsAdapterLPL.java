package com.example.paperlessquiz.loginentity;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddLoginEntitiesToParticipantsAdapterLPL implements ListParsedListener<LoginEntity> {

private ParticipantsAdapter participantsAdapter;

public AddLoginEntitiesToParticipantsAdapterLPL(ParticipantsAdapter participantsAdapter) {
        this.participantsAdapter = participantsAdapter;
        }

public void listParsed(List<LoginEntity> list) {
        participantsAdapter.addAll(list);
        }
        }
