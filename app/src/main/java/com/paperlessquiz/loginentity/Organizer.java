package com.paperlessquiz.loginentity;

import java.util.ArrayList;

/**
 * Simple extention of the User class, represents an organizer
 */
public class Organizer extends User {
    int organizerType,organizerNr;

    public Organizer(int idQuiz, int idUser, String name, String passkey, int organizerType, int organizerNr) {
        super(idQuiz, idUser, name, passkey);
        this.organizerType = organizerType;
        this.organizerNr=organizerNr;
        setDescription("Organizer " + organizerType);
    }

    public int getOrganizerType() {
        return organizerType;
    }

    public int getOrganizerNr() {
        return organizerNr;
    }
}
