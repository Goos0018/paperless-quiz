package com.example.paperlessquiz.loginentity;

import java.io.Serializable;

public class LoginEntity implements Serializable {
    private String id;
    private String name;
    private String passkey;
    private String type;

    public LoginEntity(String id, String name, String passkey, String type) {
        this.id = id;
        this.name = name;
        this.passkey = passkey;
        this.type = type;
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
}
