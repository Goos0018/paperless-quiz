package com.paperlessquiz.loginentity;

/**
 * Simple extention of the User class, represents an organizer
 */
public class Organizer extends User {
    int organizerType;

    public Organizer(int idQuiz, int idUser, String name, String passkey, int organizerType) {
        super(idQuiz, idUser, name, passkey);
        this.organizerType = organizerType;
    }

    public int getOrganizerType() {
        return organizerType;
    }
}
