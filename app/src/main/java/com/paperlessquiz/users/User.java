package com.paperlessquiz.users;

/*
This class represents a user that can log in. Will be extended to Team or Organizers.
 */
public class User {
    private int idQuiz,idUser, userNr,userType,userStatus, totalDeposits;
    private String name, description;
    private String userPassword;

    public User(int idQuiz, int idUser, int userNr, int userType, int userStatus, String name, int totalDeposits) {
        this.idQuiz = idQuiz;
        this.idUser = idUser;
        this.userNr = userNr;
        this.userType = userType;
        this.userStatus = userStatus;
        this.name = name;
        this.totalDeposits=totalDeposits;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserNr() {
        return userNr;
    }

    public void setUserNr(int userNr) {
        this.userNr = userNr;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public void updateUserBasics(User user){
        this.description = user.getDescription();
        this.name = user.getName();
        this.userStatus=user.getUserStatus();
        this.totalDeposits=user.getTotalDeposits();
    }

    public int getTotalDeposits() {
        return totalDeposits;
    }
}
