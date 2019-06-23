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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragShowRoundScore extends Fragment {

    TextView tvYourScoreForThisRnd, tvYourRankForThisRnd, tvYourScoreTotal, tvYourRankTotal;
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
        thisQuiz.calculateScores();
        if (tvYourScoreForThisRnd !=null && callingActivity!=null) {
            //tvYourScoreForThisRnd.setText(Integer.valueOf(MyApplication.theQuiz.getRoundScoreForTeam(thisTeamNr, callingActivity.getRound())).toString());
            //tvYourScoreTotal.setText(Integer.valueOf(MyApplication.theQuiz.getTotalScoreForTeam(thisTeamNr, callingActivity.getRound())).toString());
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
        tvYourRankTotal = v.findViewById(R.id.tvYourRankTotal);
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
