package com.paperlessquiz.quiz;

import com.paperlessquiz.answer.Answer;
import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.question.Question;
import com.paperlessquiz.round.Round;

import java.util.ArrayList;
import java.util.Collections;

public class QuizStandingsCalculator {
    private Quiz thisQuiz;

    //Calculate scores for the entire quiz based on corrections and store them in the results objects for each team
    public void calculateScores() {
        //for each team
        for (int i = 0; i < thisQuiz.getTeams().size(); i++) {
            int teamNr = i + 1;
            int totalScoreUntilThisRound = 0;
            Team thisTeam = thisQuiz.getTeam(teamNr);
            //for each round
            for (int j = 0; j < thisQuiz.getRounds().size(); j++) {
                int roundNr = j + 1;
                Round thisRnd = thisQuiz.getRound(roundNr);
                int scoreForThisRnd = 0;
                if (thisRnd.isCorrected()) {
                    //for each question in round j
                    for (int k = 0; k < thisQuiz.getRounds().get(j).getQuestions().size(); k++) {
                        int questionNr = k + 1;
                        Question thisQuestion = thisQuiz.getQuestion(roundNr, questionNr);
                        Answer thisAnswer = thisQuiz.getAnswerForTeam(roundNr, questionNr, teamNr);
                        if (thisAnswer.isCorrect()) {
                            scoreForThisRnd = scoreForThisRnd + thisQuestion.getMaxScore();
                        }
                    }
                }
                totalScoreUntilThisRound = totalScoreUntilThisRound + scoreForThisRnd;
                thisTeam.getResultAfterRound(roundNr).setScoreForThisRound(scoreForThisRnd);
                thisTeam.getResultAfterRound(roundNr).setTotalScoreAfterThisRound(totalScoreUntilThisRound);
            }
        }
    }

    //Calculate standings for the entire quiz, based on the scores calculated per team
    //Build an array
    public void calculateStandings() {
        int teamNr, roundNr;
        //for each round
        for (int j = 0; j < thisQuiz.getRounds().size(); j++) {
            ArrayList<ScoreForTeam> allScoresForThisRound = new ArrayList<>(); //This contains the score for all teams for a round
            ArrayList<ScoreForTeam> allTotalScoresAfterThisRound = new ArrayList<>(); //This contains the total score for all teams after a round
            roundNr = j + 1;
            Round thisRnd = thisQuiz.getRound(roundNr);
            if (thisRnd.isCorrected()) {
                //for each team
                for (int i = 0; i < thisQuiz.getTeams().size(); i++) {
                    teamNr = i + 1;
                    Team thisTeam = thisQuiz.getTeam(teamNr);
                    allScoresForThisRound.add(new ScoreForTeam(teamNr, thisTeam.getResultAfterRound(roundNr).getScoreForThisRound()));
                    allTotalScoresAfterThisRound.add(new ScoreForTeam(teamNr, thisTeam.getResultAfterRound(roundNr).getTotalScoreAfterThisRound()));
                }
            }
            //Now we sort allScoresForThisRound per score
            Collections.sort(allScoresForThisRound);
            Collections.sort(allTotalScoresAfterThisRound);
            //Loop over the (sorted) array and assign each team the standing it has
            for (int i = 0; i < allScoresForThisRound.size(); i++) {
                Team theTeam;
                theTeam = thisQuiz.getTeam(allScoresForThisRound.get(i).teamNr);
                theTeam.getResultAfterRound(roundNr).setPosInStandingForThisRound(i + 1);
            }
            for (int i = 0; i < allTotalScoresAfterThisRound.size(); i++) {
                Team theTeam;
                theTeam = thisQuiz.getTeam(allTotalScoresAfterThisRound.get(i).teamNr);
                theTeam.getResultAfterRound(roundNr).setPosInStandingAfterThisRound(i + 1);
            }
        }
    }

    //This class is used to store a teamNumber + a score
    //We will create an array of those to represent all Team  scores and sort this array by score to get the standings
    private class ScoreForTeam implements Comparable<ScoreForTeam> {
        int teamNr;
        int score;

        @Override
        public int compareTo(ScoreForTeam o) {
            return o.score - score;
        }

        public ScoreForTeam(int teamNr, int score) {
            this.teamNr = teamNr;
            this.score = score;
        }

    }


    public QuizStandingsCalculator(Quiz thisQuiz) {
        this.thisQuiz = thisQuiz;
    }
}
