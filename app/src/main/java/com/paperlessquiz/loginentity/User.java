package com.paperlessquiz.loginentity;

/*
This class represents a user that can log in. Will be extended to Team or Organizers.
 */
public class User {
    private int idQuiz,idUser;
    private String name;
    private String passkey;

    public User(int idQuiz, int idUser, String name, String passkey) {
        this.idQuiz = idQuiz;
        this.idUser = idUser;
        this.name = name;
        this.passkey = passkey;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }
}
