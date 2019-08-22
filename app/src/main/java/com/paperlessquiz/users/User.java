package com.paperlessquiz.users;

import com.paperlessquiz.quiz.QuizDatabase;

/*
This class represents a user that can log in. Will be extended to Team or Organizers.
 */
public class User {
    private int idQuiz,idUser, userNr,userType,userStatus, userCredits, totalSpent, totalTimePaused;
    private String name, description;
    private String userPassword;

    public User(int idQuiz, int idUser, int userNr, int userType, int userStatus, String name, int userCredits, int totalSpent, int totalTimePaused) {
        this.idQuiz = idQuiz;
        this.idUser = idUser;
        this.userNr = userNr;
        this.userType = userType;
        this.userStatus = userStatus;
        this.name = name;
        this.userCredits = userCredits;
        this.totalSpent=totalSpent;
        this.totalTimePaused=totalTimePaused;
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
        switch (user.getUserType()) {
            case QuizDatabase.USERTYPE_TEAM:
                this.description ="Ploeg " + getUserNr();
                break;
            case QuizDatabase.USERTYPE_QUIZMASTER:
                this.description = "Quizmaster";
                break;
            case QuizDatabase.USERTYPE_CORRECTOR:
                setDescription("Verbeteraar");
                break;
            case QuizDatabase.USERTYPE_RECEPTIONIST:
                setDescription("Receptionist");
                break;
            case QuizDatabase.USERTYPE_JUROR:
                setDescription("Juryvoorzitter");
                break;
//            case QuizDatabase.USERTYPE_SALES:
//                setDescription("Bonnetjes");
//                break;
            case QuizDatabase.USERTYPE_BARRESPONSIBLE:
                setDescription("Barverantwoordelijke");
                break;
            case QuizDatabase.USERTYPE_BARHELPER:
                setDescription("Barhelper");
                break;
            default:
                setDescription("Organizator");
        }
        this.name = user.getName();
        this.userStatus=user.getUserStatus();
        this.userCredits =user.getUserCredits();
        this.totalSpent=user.getTotalSpent();
        this.totalTimePaused=user.getTotalTimePaused();
    }

    public int getUserCredits() {
        return userCredits;
    }

    public int getTotalSpent() {
        return totalSpent;
    }

    public int getTotalTimePaused() {
        return totalTimePaused;
    }
}
