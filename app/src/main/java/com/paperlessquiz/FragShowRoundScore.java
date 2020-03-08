package com.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.ResultAfterRound;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragShowRoundScore extends Fragment {

    TextView tvYourScoreForThisRnd, tvYourScoreTotal, tvYourRankAfterThisRound,tvMaxRndScore, tvMaxTotalScore, tvNrOfTeams2;
    ImageView ivTopScore;
    int thisTeamNr, thisRoundNr;
    HasShowRoundScore callingActivity;
    Quiz thisQuiz = MyApplication.theQuiz;
    int colorAlert, colorNormal;

    public interface HasShowRoundScore{
        int getRound();
        int getTeam();
    };

    public FragShowRoundScore() {
        // Required empty public constructor
    }

    public void refresh() {

        if (tvYourScoreForThisRnd !=null && callingActivity!=null) {
            thisRoundNr = callingActivity.getRound();
            thisTeamNr = callingActivity.getTeam();
            ResultAfterRound resultAfterRound = thisQuiz.getResultForTeam(thisTeamNr, thisRoundNr);
            if (resultAfterRound != null) {
                int yourScoreForThisRound = resultAfterRound.getScoreForThisRound();
                int maxScoreForThisRound = resultAfterRound.getMaxScoreForThisRound();
                int yourTotalScoreAfterThisRound = resultAfterRound.getTotalScoreAfterThisRound();
                int maxTotalScoreAfterThisRound = resultAfterRound.getMaxTotalScoreAfterThisRound();
                int yourRankAfterThisRound = resultAfterRound.getPosInStandingAfterThisRound();
                //If you are in the top X, we just report this instead of your actual position
                tvYourScoreForThisRnd.setText(Integer.toString(yourScoreForThisRound));
                tvYourScoreTotal.setText(Integer.toString(yourTotalScoreAfterThisRound));
                tvYourRankAfterThisRound.setText(Integer.toString(yourRankAfterThisRound));
                tvMaxRndScore.setText("/ " + maxScoreForThisRound);
                tvMaxTotalScore.setText("/ " + maxTotalScoreAfterThisRound);
                tvNrOfTeams2.setText("/ " + thisQuiz.getTeams().size());

                //If you are in the Top X, show this
                if (yourRankAfterThisRound <=thisQuiz.getHideTopRows())
                {
                    //Option 1: show icon
                    //tvYourRankAfterThisRound.setVisibility(View.GONE);
                    //tvNrOfTeams2.setVisibility(View.GONE);//Text(" ");
                    //ivTopScore.setVisibility(View.VISIBLE);

                    //Option 2: show top X
                    tvYourRankAfterThisRound.setVisibility(View.GONE);
                    tvNrOfTeams2.setText("Top " + thisQuiz.getHideTopRows() + "!!!");
                    tvNrOfTeams2.setTextColor(colorAlert);
                }
                else // reset visibility
                {
                    tvYourRankAfterThisRound.setVisibility(View.VISIBLE); //Text("Top " + thisQuiz.getHideTopRows() + "!!!");
                    tvNrOfTeams2.setTextColor(colorNormal);
                    //For option 1
                    //tvNrOfTeams2.setVisibility(View.VISIBLE);//Text(" ");
                    //ivTopScore.setVisibility(View.GONE);
                }


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_show_round_score, container, false);
        tvYourScoreForThisRnd = v.findViewById(R.id.tvYourScoreForThisRnd);
        tvYourScoreTotal = v.findViewById(R.id.tvYourScoreTotal);
        tvYourRankAfterThisRound = v.findViewById(R.id.tvYourRankTotal);
        tvMaxRndScore = v.findViewById(R.id.tvMaxRndScore);
        tvMaxTotalScore = v.findViewById(R.id.tvMaxTotalScore);
        tvNrOfTeams2 = v.findViewById(R.id.tvNrOfTeams2);
        ivTopScore=v.findViewById(R.id.ivTopScore);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callingActivity=(HasShowRoundScore) context;
        colorAlert = context.getResources().getColor(R.color.wrongRed);
        colorNormal = context.getResources().getColor(R.color.colorPrimaryDark);
        //thisRoundNr= callingActivity.getRound();
        //thisTeamNr=callingActivity.getTeam();
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
