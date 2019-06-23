package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizStandingsCalculator;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragShowRoundScore extends Fragment {

    TextView tvYourScoreForThisRnd, tvYourRankForThisRnd, tvYourScoreTotal, tvYourRankAfterThisRound;
    int thisTeamNr, thisRoundNr;
    HasShowRoundScore callingActivity;
    Quiz thisQuiz = MyApplication.theQuiz;

    public interface HasShowRoundScore{
        int getRound();
        int getTeam();
    };

    public FragShowRoundScore() {
        // Required empty public constructor
    }

    public void refresh() {
        QuizStandingsCalculator quizStandingsCalculator = new QuizStandingsCalculator(thisQuiz);
        quizStandingsCalculator.calculateScores();
        quizStandingsCalculator.calculateStandings();
        if (tvYourScoreForThisRnd !=null && callingActivity!=null) {
            int yourScoreForThisRound = thisQuiz.getTeam(thisTeamNr).getResultAfterRound(callingActivity.getRound()).getScoreForThisRound();
            int yourTotalScoreAftertHisRound = thisQuiz.getTeam(thisTeamNr).getResultAfterRound(callingActivity.getRound()).getTotalScoreAfterThisRound();
            int yourRankForThisRound = thisQuiz.getTeam(thisTeamNr).getResultAfterRound(callingActivity.getRound()).getPosInStandingForThisRound();
            int yourRankAfterThisRound = thisQuiz.getTeam(thisTeamNr).getResultAfterRound(callingActivity.getRound()).getPosInStandingAfterThisRound();
            tvYourScoreForThisRnd.setText(Integer.toString(yourScoreForThisRound));
            tvYourScoreTotal.setText(Integer.toString(yourTotalScoreAftertHisRound));
            tvYourRankForThisRnd.setText(Integer.toString(yourRankForThisRound));
            tvYourRankAfterThisRound.setText(Integer.toString(yourRankAfterThisRound));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_show_round_score, container, false);
        tvYourScoreForThisRnd = v.findViewById(R.id.tvYourScoreForThisRnd);
        tvYourRankForThisRnd = v.findViewById(R.id.tvYourRankForThisRnd);
        tvYourScoreTotal = v.findViewById(R.id.tvYourScoreTotal);
        tvYourRankAfterThisRound = v.findViewById(R.id.tvYourRankTotal);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callingActivity=(HasShowRoundScore) context;
        thisRoundNr= callingActivity.getRound();
        thisTeamNr=callingActivity.getTeam();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    public void setThisTeamNr(int thisTeamNr) {
        this.thisTeamNr = thisTeamNr;
    }

    public void setThisRoundNr(int thisRoundNr) {
        this.thisRoundNr = thisRoundNr;
    }
}
