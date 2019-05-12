package com.example.paperlessquiz.loginentity;

import java.io.Serializable;

/*
This class represents a user that can log in. Either a participant team or one of the organizers
 */
public class LoginEntity implements Serializable {
    public static final String INTENT_EXTRA_NAME_THIS_LOGIN_TYPE = "thisLoginType";
    public static final String SELECTION_PARTICIPANT = "Participant";
    public static final String SELECTION_ORGANIZER = "Organizer";
    public static final String INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY = "thisLoginEntity";
    private String id;
    private String name;
    private String passkey;
    private String type;

    public LoginEntity(String id, String type, String name, String passkey) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.passkey = passkey;
    }

    public String getName() {
        return name;
    }

    public String getPasskey() {
        return passkey;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
