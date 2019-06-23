package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.question.Question;
import com.example.paperlessquiz.round.Round;

public class QuizStandingsCalculator {
    private Quiz thisQuiz;

    //Calculate scores for the entire quiz based on corrections and store them in the results objects for each team
    public void calculateScores() {
        //for each team
        for (int i = 0; i < thisQuiz.getTeams().size(); i++) {
            int teamNr = i + 1;
            LoginEntity thisTeam = thisQuiz.getTeam(teamNr);
            //for each round
            for (int j = 0; j < thisQuiz.getRounds().size(); j++) {
                int roundNr = j + 1;
                Round thisRnd = thisQuiz.getRound(roundNr);
                int scoreForThisRnd = 0;
                int totalScoreUntilThisRound = 0;
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
                //setRoundScoreForTeam(teamNr, roundNr, scoreForThisRnd);

                //setTotalScoreAfterRound(teamNr,roundNr,totalScoreUntilThisRound);

            }
        }
    }




    public QuizStandingsCalculator(Quiz thisQuiz) {
        this.thisQuiz = thisQuiz;
    }
}
