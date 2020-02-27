package com.paperlessquiz.quiz;

import com.paperlessquiz.users.Organizer;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class contains all data we need for a quiz:
 * - Arrylists of teams, rounds and organizers
 * - Organizers are simply used to do things like open and lcose roudns, correct rounds, ... .
 * - Each Round object contains status information for that round (corrected, closed, ...), and most importantly, a list of questions for that round.
 * - Each question contains some general information about that question (for example, the correct answer), but also an arraylist of al answers that were given.
 * Here, answer(i) is the answer from the team with teamnumber i, and the Asnwer object not only contains the String that is the answser,
 * but also an indication whether it is corrected, and if the answer is correct or not.
 * - Each Team (Team)finally, has two arraylists, where each memeber cooresponds to a round:
 * * An indication whether or not answers were subnmitted for this round
 * * A RoundResults object, that contains the score for this round, the total score until this round, and the position of this team in the standings for the round and in ttoal after the round
 * <p>
 * A Quiz is created by loading it from a Google sheet that contains various sheets. The QuizLoader object is responsible for loading al data.
 * When the quiz is initially loaded, we properly initiliaze the central quiz object that is part of the MyApplication class.
 * After that, the loader automatically updates the quiz object.
 */

public class Quiz implements Serializable {
    public static final int TARGET_WIDTH = 200;
    public static final int TARGET_HEIGHT = 200;
    public static final int ACTIONBAR_ICON_WIDTH = 125;
    public static final int ACTIONBAR_ICON_HEIGHT = 125;

    private QuizListData listData;
    private ArrayList<Team> teams;
    private ArrayList<Organizer> organizers;
    private ArrayList<Round> rounds;
    private User thisUser;
    private Organizer thisOrganizer;
    public boolean loadingCompleted = false;
    private HashMap<String,ResultAfterRound> resultsForTeamAfterRound;
    private int hideTopRows = 5;

    //20190728 - We only need an empty constructor, the QuizLoader class will populate all fields of the quiz
    public Quiz() {
        this.listData = new QuizListData();
        this.teams = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.rounds = new ArrayList<>();
        resultsForTeamAfterRound = new HashMap<>();
    }

    //20190728 - Make sure that all teams have a blank answer for all questions. Used initially, afterwards, we will overwrite this with the answers that exist in the SQL db
    public void initializeAllAnswers() {
        //For each round
        for (int i = 0; i < rounds.size(); i++) {
            Round theRound = rounds.get(i);
            //For each question in this round
            for (int j = 0; j < theRound.getQuestions().size() ; j++) {
                Question theQuestion = theRound.getQuestions().get(j);
                //For each Team
                for (int k = 0; k < teams.size(); k++) {
                    Team theTeam = teams.get(k);
                    theQuestion.getAllAnswers().add(new Answer(theTeam.getUserNr(), theQuestion.getRoundNr(), theQuestion.getQuestionNr(), "", false, false,false));
                }
            }
        }
    }

    //20190730 - Add a result for a given team and Round
public void addResult(ResultAfterRound resultAfterRound){
    //Create an index
    String index = resultAfterRound.getTeamNr() + "." + resultAfterRound.getRoundNr();
        resultsForTeamAfterRound.put(index,resultAfterRound);
}

public ResultAfterRound getResultForTeam(int teamNr, int roundNr){
        String index = teamNr + "." + roundNr;
        return resultsForTeamAfterRound.get(index);
}

    //Get the question ID for the specified question
    public int getQuestionID(int roundNr, int questionNr){
        return getQuestion(roundNr,questionNr).getIdQuestion();
    }

    //Initialize the Results for each team - just make sure the necessary array elements exist so they can be used later
    public void initializeResultsForTeams() {
        for (int i = 0; i < teams.size(); i++) {
            //Make sure we already have something - should not be necessary since included in constructor for Team
            if (teams.get(i).getResults() == null) {
                teams.get(i).setResults(new ArrayList<>());
            }
            //Make sure we have a Results object for each round
            for (int j = 0; j < rounds.size(); j++) {
                if (teams.get(i).getResults().size() < j + 1) {
                    teams.get(i).getResults().add(new ResultAfterRound());
                }
            }

        }
    }

    /*
    //Add the questions information we have from an array of array of questions, first dimension is the round.
    public void setAllQuestionsPerRound(ArrayList<ArrayList<Question>> allQuestionsPerRound) {
        //For each entry in the allQuestionsPerRound array (=for each round)
        for (int i = 0; i < allQuestionsPerRound.size(); i++) {
            this.getRounds().get(i).setQuestions(allQuestionsPerRound.get(i));
            this.getRounds().get(i).setNrOfQuestions(allQuestionsPerRound.get(i).size());
        }
    }
    */

    /*
    //Add the answers for each question from an array of array of AnswersLists. Answers here means simply the Strings, not the Answer object
    public void updateAllAnswersPerQuestion(ArrayList<ArrayList<AnswersList>> allAnswersPerRound) {
        //For each entry in the allAnswersPerRound array (=for each round)
        for (int i = 0; i < allAnswersPerRound.size(); i++) {
            ArrayList<AnswersList> allAnswersForThisRound = allAnswersPerRound.get(i);
            //For each question in this round
            for (int j = 0; j < allAnswersForThisRound.size(); j++) {
                AnswersList allAnswersForThisQuestion = allAnswersForThisRound.get(j);
                this.getRounds().get(i).getQuestions().get(j).updateAllAnswers(allAnswersForThisQuestion.getAllAnswers());
            }

        }
    }
    */

    /*
    //Add the answers for each question from an array of array of AnswersLists. Answers here means the entire Answer object
    public void setAllAnswersPerQuestion(ArrayList<ArrayList<AnswersList>> allAnswersPerRound) {
        //For each entry in the allAnswersPerRound array (=for each round)
        for (int i = 0; i < allAnswersPerRound.size(); i++) {
            ArrayList<AnswersList> allAnswersForThisRound = allAnswersPerRound.get(i);
            //For each question in this round
            for (int j = 0; j < allAnswersForThisRound.size(); j++) {
                AnswersList allAnswersForThisQuestion = allAnswersForThisRound.get(j); //This contains all answers for each team for this question
                this.getRounds().get(i).getQuestions().get(j).setAllAnswers(allAnswersForThisQuestion.getAllAnswers());
            }

        }
    }


    //Add the answers for each question from an array of array of AnswersLists
    public void setAllCorrectionsPerQuestion(ArrayList<ArrayList<CorrectionsList>> allCorrectionsPerRound) {
        //For each entry in the allAnswersPerRound array (=for each round)
        for (int i = 0; i < allCorrectionsPerRound.size(); i++) {
            int roundNr = i + 1;
            ArrayList<CorrectionsList> allCorrectionsForThisRound = allCorrectionsPerRound.get(i);
            //For each question in this round
            for (int j = 0; j < allCorrectionsForThisRound.size(); j++) {
                int questionNr = j + 1;
                CorrectionsList allCorrectionsForThisQuestion = allCorrectionsForThisRound.get(j);
                //Foreach answer to this question
                for (int k = 0; k < allCorrectionsForThisQuestion.getAllCorrections().size(); k++) {
                    int teamNr = k + 1;
                    getAnswerForTeam(roundNr, questionNr, teamNr).setCorrect(allCorrectionsForThisQuestion.getAllCorrections().get(k).isCorrect());
                    getAnswerForTeam(roundNr, questionNr, teamNr).setCorrected(allCorrectionsForThisQuestion.getAllCorrections().get(k).isCorrected());
                }
            }
        }
    }
    */

    //Return the team/organizer with the given ID
    public Team getTeam(int teamNr) {
        return teams.get(teamNr - 1);
    }

    //Return the team with the given ID
    public Organizer getOrganizer(int organizerNr) {
        return organizers.get(organizerNr - 1);
    }


    public ArrayList<Team> getTeams() {
        return teams;
    }

    public ArrayList<Organizer> getOrganizers() {
        return organizers;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void setOrganizers(ArrayList<Organizer> organizers) {
        this.organizers = organizers;
    }

    public QuizListData getListData() {
        return listData;
    }

    public void setListData(QuizListData listData) {
        this.listData = listData;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public Round getRound(int rndNr) {
        return rounds.get(rndNr - 1);
    }

    public Question getQuestion(int rndNr, int questionNr) {
        return getRound(rndNr).getQuestion(questionNr);
    }

    public Answer getAnswerForTeam(int rndNr, int questionNr, int teamNr) {
        return getQuestion(rndNr, questionNr).getAnswerForTeam(teamNr);
    }

    public void setAnswerForTeamSubmitted(int roundNr, int questionNr, int teamNr){
        getAnswerForTeam(roundNr,questionNr,teamNr).setSubmitted(true);
    };

    public boolean isAnswerSubmitted(int roundNr, int questionNr, int teamNr){
        return getAnswerForTeam(roundNr,questionNr,teamNr).isSubmitted();
    };

    public void setAnswerForTeam(int rndNr, int questionNr, int teamNr, String answer) {
        getQuestion(rndNr, questionNr).setAnswerForTeam(teamNr, answer);
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
        //this.additionalData.setNrOfRounds(rounds.size());
    }

    public ArrayList<Answer> getAnswersForRound(int rndNr, int teamNr) {
        ArrayList<Answer> answersList = new ArrayList<>();
        for (int i = 0; i < getRound(rndNr).getQuestions().size(); i++) {
            answersList.add(i, getAnswerForTeam(rndNr, i + 1, teamNr)); //The
        }
        return answersList;

    }

    public ArrayList<Answer> getAllAnswersForQuestion(int rndNr, int questionNr) {
        return getQuestion(rndNr, questionNr).getAllAnswers();
    }

    public User getThisUser() {
        return thisUser;
    }

    public void setThisUser(User thisUser) {
        this.thisUser = thisUser;
    }

    public boolean isAnyRoundOpen() {
        boolean res = false;
        for (int i = 0; i < rounds.size(); i++) {
            res = res || (rounds.get(i).getRoundStatus()== QuizDatabase.ROUNDSTATUS_OPENFORANSWERS);
        }
        return res;
    }

    public int getMaxScoreUntilRound(int roundNr) {
        int res = 0;
        for (int i = 0; i < roundNr; i++) {
            res = res + rounds.get(i).getMaxScore();
        }
        return res;
    }


    public ArrayList<User> convertOrganizerToUserArray(ArrayList<Organizer> organizers) {
        ArrayList<User> userArray = new ArrayList<>();
        for (int i = 0; i < organizers.size(); i++) {
            userArray.add((User) organizers.get(i));
        }
        return userArray;
    }

    public ArrayList<User> convertTeamToUserArray(ArrayList<Team> teams) {
        ArrayList<User> userArray = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            userArray.add((User) teams.get(i));
        }
        return userArray;
    }

    public Organizer getThisOrganizer() {
        return thisOrganizer;
    }

    public void setThisOrganizer(Organizer thisOrganizer) {
        this.thisOrganizer = thisOrganizer;
    }

    public int getHideTopRows() {
        return hideTopRows;
    }

    public void setHideTopRows(int hideTopRows) {
        this.hideTopRows = hideTopRows;
    }
}
