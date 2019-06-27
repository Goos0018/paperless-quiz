package com.example.paperlessquiz.quiz;

import android.content.Context;

import com.example.paperlessquiz.corrections.CorrectionsList;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.answerslist.AnswersList;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.google.access.LoadingListenerSilent;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.round.Round;

import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {
    public static final int TARGET_WIDTH = 200;
    public static final int TARGET_HEIGHT = 200;
    public static final int ACTIONBAR_ICON_WIDTH = 125;
    public static final int ACTIONBAR_ICON_HEIGHT = 125;
    private QuizListData listData;
    private ArrayList<LoginEntity> teams;
    private ArrayList<LoginEntity> organizers;
    private ArrayList<Round> rounds;
    private LoginEntity myLoginentity;
    //public QuizLoader loader;
    public boolean loadingCompleted = false;
    //public ArrayList<ArrayList<Integer>> totalScoreAfterEachRoundPerTeam;

    //We only need an empty constructor, the QuizLoader class will populate all fields of the quiz
    public Quiz() {
        this.listData = new QuizListData();
        //this.additionalData = new QuizExtraData();
        this.teams = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.rounds = new ArrayList<>();
        //this.totalScoreAfterEachRoundPerTeam = new ArrayList<>();
        //this.allScoresPerTeam = new ArrayList<>();
        //for (int i = 0; i < this.additionalData.getNrOfRounds(); i++) {
        //    rounds.add(i, new Round());
        //}
    }


    /*
        public void setTotalScoreAfterRound(int teamNr, int roundNr,int score){
            totalScoreAfterEachRoundPerTeam.get(teamNr-1).set(roundNr - 1, new Integer(score));
        }

            //Get the total score for team after round roundNr
    public int getTotalScoreForTeam(int teamId, int roundNr) {
        return totalScoreAfterEachRoundPerTeam.get(teamId - 1).get(roundNr - 1);
    }

    //Get round score for team
    public int getRoundScoreForTeam(int teamId, int roundId) {
        return allScoresPerTeam.get(teamId - 1).getScorePerRoundForTeam().get(roundId - 1);
    }

    //Set round score for team
    public void setRoundScoreForTeam(int teamId, int roundNr, int score) {
        allScoresPerTeam.get(teamId - 1).setScoreForRound(roundNr, score);
    }

    public ArrayList<Score> getAllScoresPerTeam() {
        return allScoresPerTeam;
    }

    public void setAllScoresPerTeam(ArrayList<Score> allScoresPerTeam) {
        this.allScoresPerTeam = allScoresPerTeam;
    }

    public void initializeTotalScoreAfterEachRound() {
        for (int i = 0; i < this.teams.size(); i++) {
            totalScoreAfterEachRoundPerTeam.add(new ArrayList<>());
            for (int j = 0; j < this.rounds.size(); j++) {
                totalScoreAfterEachRoundPerTeam.get(i).add(new Integer(0));
            }
        }
    }

    */
    //Initialize the Results for each team
    public void initializeResultsForTeams(){
        for (int i = 0; i < teams.size() ; i++) {
            //Make sure we already have something - should not be necessary since included in constructor for Team
            if (teams.get(i).getResults() == null){
                teams.get(i).setResults(new ArrayList<>());
            }
            //Make sure we have a Results object for each round
            for (int j = 0; j < rounds.size(); j++) {
                if(teams.get(i).getResults().size() < j+1){
                    teams.get(i).getResults().add(new ResultAfterRound());
                }
            }

        }
    }



    //Add the questions information we have from an array of array of questions.
    public void setAllQuestionsPerRound(ArrayList<ArrayList<Question>> allQuestionsPerRound) {
        //For each entry in the allQuestionsPerRound array (=for each round)
        for (int i = 0; i < allQuestionsPerRound.size(); i++) {
            this.getRounds().get(i).setQuestions(allQuestionsPerRound.get(i));
            this.getRounds().get(i).setNrOfQuestions(allQuestionsPerRound.get(i).size());
        }
    }

    //Add the answers for each question from an array of array of AnswersLists
    public void setAllAnswersPerQuestion(ArrayList<ArrayList<AnswersList>> allAnswersPerRound) {
        //For each entry in the allAnswersPerRound array (=for each round)
        for (int i = 0; i < allAnswersPerRound.size(); i++) {
            ArrayList<AnswersList> allAnswersForThisRound = allAnswersPerRound.get(i);
            //For each question in this round
            for (int j = 0; j < allAnswersForThisRound.size(); j++) {
                AnswersList allAnswersForThisQuestion = allAnswersForThisRound.get(j);
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


    //Return the team/organizer with the given ID
    public LoginEntity getTeam(int teamNr) {
        return teams.get(teamNr - 1);
    }

    //Return the team with the given ID
    public LoginEntity getOrganizer(int organizerNr) {
        return organizers.get(organizerNr - 1);
    }


    public ArrayList<LoginEntity> getTeams() {
        return teams;
    }

    public ArrayList<LoginEntity> getOrganizers() {
        return organizers;
    }

    public void setTeams(ArrayList<LoginEntity> teams) {
        this.teams = teams;
        //this.additionalData.setNrOfParticipants(teams.size());
    }

    public void setOrganizers(ArrayList<LoginEntity> organizers) {
        this.organizers = organizers;
    }

    public QuizListData getListData() {
        return listData;
    }

    public void setListData(QuizListData listData) {
        this.listData = listData;
    }

    //public QuizExtraData getAdditionalData() {
    //    return additionalData;
    //}

    //public void setAdditionalData(QuizExtraData additionalData) {
    //    this.additionalData = additionalData;
    //}

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

    public LoginEntity getMyLoginentity() {
        return myLoginentity;
    }

    public void setMyLoginentity(LoginEntity myLoginentity) {
        this.myLoginentity = myLoginentity;
    }

    public void updateRounds(Context context) {
        ArrayList<Round> roundsList = getRounds();
        String tmp = "[";
        for (int i = 0; i < roundsList.size(); i++) {
            tmp = tmp + roundsList.get(i).toString();
            if (i < roundsList.size() - 1) {
                tmp = tmp + ",";
            } else {
                tmp = tmp + "]";
            }
        }
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_ROUNDS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_RECORD_ID + "1" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_FIELDNAME + QuizGenerator.ROUND_NAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
        GoogleAccessSet submitRounds = new GoogleAccessSet(context, scriptParams);
        submitRounds.setData(new LoadingListenerNotify(context, getMyLoginentity().getName(),
                "Submitting round updates"));
    }

    public void updateTeams(Context context) {
        ArrayList<LoginEntity> teamsList = getTeams();
        String tmp = "[";
        for (int i = 0; i < teamsList.size(); i++) {
            tmp = tmp + teamsList.get(i).toString();
            if (i < teamsList.size() - 1) {
                tmp = tmp + ",";
            } else {
                tmp = tmp + "]";
            }
        }
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_TEAMS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_RECORDID + GoogleAccess.PARAMVALUE_FIRST_ROUNDNR + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_FIELDNAME + QuizGenerator.LOGIN_ENTITY_NAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
        GoogleAccessSet googleAccessSet = new GoogleAccessSet(context, scriptParams);
        googleAccessSet.setData(new LoadingListenerNotify(context, getMyLoginentity().getName(),
                "Submitting team updates"));
    }

    //Used by the Corrector to submit all corrections for a single question
    public void submitCorrectionsForQuestion(Context context, int roundNr, int questionNr) {
        ArrayList<Answer> answerList = getAllAnswersForQuestion(roundNr, questionNr);
        String tmp = "[[";
        for (int i = 0; i < answerList.size(); i++) {
            tmp = tmp + "\"" + answerList.get(i).isCorrect() + "\"";
            if (i < answerList.size() - 1) {
                tmp = tmp + ",";
            }
        }
        tmp = tmp + "]]";
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_CORRECTIONS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_RECORDID + getQuestion(roundNr, questionNr).getQuestionID() + GoogleAccess.PARAM_CONCATENATOR +
                //We write score starting from the first team which should have id 1
                GoogleAccess.PARAMNAME_FIELDNAME + QuizGenerator.TEAMS_PREFIX + GoogleAccess.PARAMVALUE_FIRST_TEAM_NR + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
        GoogleAccessSet submitScores = new GoogleAccessSet(context, scriptParams);
        submitScores.setData(new LoadingListenerNotify(context, getMyLoginentity().getName(),
                "Submitting scores for question " + getQuestion(roundNr, questionNr).getQuestionID()));
    }

    //Used by the Corrector to submit all corrections for a single team
    public void submitCorrectionsForTeam(Context context, int roundNr, int teamNr) {
        ArrayList<Answer> answerList = getAnswersForRound(roundNr, teamNr);
        String tmp = "[";
        for (int i = 0; i < answerList.size(); i++) {
            tmp = tmp + "[\"" + answerList.get(i).isCorrect() + "\"]";
            if (i < answerList.size() - 1) {
                tmp = tmp + ",";
            }
        }
        tmp = tmp + "]";
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_CORRECTIONS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_RECORDID + getQuestion(roundNr, 1).getQuestionID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_FIELDNAME + QuizGenerator.TEAMS_PREFIX + teamNr + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
        GoogleAccessSet submitScores = new GoogleAccessSet(context, scriptParams);
        submitScores.setData(new LoadingListenerNotify(context, getMyLoginentity().getName(),
                "Submitting scores for team " + getTeam(teamNr).getName()));
    }

    //Used by the Participant to submit all answers for a single round
    public void submitAnswers(Context context, int roundNr) {
        ArrayList<Answer> answerList = getAnswersForRound(roundNr, getMyLoginentity().getId());
        String tmp = "[";
        for (int i = 0; i < answerList.size(); i++) {
            tmp = tmp + "[\"" + answerList.get(i).getTheAnswer() + "\"]";
            if (i < answerList.size() - 1) {
                tmp = tmp + ",";
            }
        }
        tmp = tmp + "]";
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + listData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_USERID + myLoginentity.getName() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ROUNDID + roundNr + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ROUND_PREFIX + QuizGenerator.ROUNDS_PREFIX + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_FIRSTQUESTION + getRound(roundNr).getQuestion(1).getQuestionID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_TEAMID + myLoginentity.getId() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_TEAM_PREFIX + QuizGenerator.TEAMS_PREFIX + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ANSWERS + tmp + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SUBMITANSWERS;
        GoogleAccessSet submitAnswers = new GoogleAccessSet(context, scriptParams);
        submitAnswers.setData(new LoadingListenerNotify(context, myLoginentity.getName(),
                "Submitting answers for round " + roundNr));
    }


    //Triggered in the onCreate of ParticipantHome - marks this team as Logged In
    public void setLoggedIn(Context context, int teamNr, boolean isLoggedIn) {
        String tmp = "[[" + isLoggedIn + "]]";
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_USERID + "Rupert" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizGenerator.SHEET_TEAMS + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_RECORDID + teamNr + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_FIELDNAME + QuizGenerator.TEAM_LOGGED_IN + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
        GoogleAccessSet setLoggedIn = new GoogleAccessSet(context, scriptParams);
        setLoggedIn.setData(new LoadingListenerSilent());
    }


}
