package com.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.R;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizStandingsCalculator;
import com.paperlessquiz.quiz.ResultAfterRound;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragShowRoundScore extends Fragment {

    TextView tvYourScoreForThisRnd, tvYourRankForThisRnd, tvYourScoreTotal, tvYourRankAfterThisRound,tvMaxRndScore, tvMaxTotalScore, tvNrOfTeams, tvNrOfTeams2;
    ImageView ivTopScore;
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

        if (tvYourScoreForThisRnd !=null && callingActivity!=null) {
            thisRoundNr = callingActivity.getRound();
            thisTeamNr = callingActivity.getTeam();
            ResultAfterRound resultAfterRound = thisQuiz.getResultForTeam(thisTeamNr, thisRoundNr);
            if (resultAfterRound != null) {
                int yourScoreForThisRound = resultAfterRound.getScoreForThisRound();
                int yourTotalScoreAfterThisRound = resultAfterRound.getTotalScoreAfterThisRound();
                int yourRankForThisRound = resultAfterRound.getPosInStandingForThisRound();
                int yourRankAfterThisRound = resultAfterRound.getPosInStandingAfterThisRound();
                //If you are in the top X, we just report this instead of your actual position
                tvYourScoreForThisRnd.setText(Integer.toString(yourScoreForThisRound));
                tvYourScoreTotal.setText(Integer.toString(yourTotalScoreAfterThisRound));
                tvYourRankForThisRnd.setText(Integer.toString(yourRankForThisRound));
                tvYourRankAfterThisRound.setText(Integer.toString(yourRankAfterThisRound));
                tvMaxRndScore.setText("/ " + thisQuiz.getRound(thisRoundNr).getMaxScore());
                tvMaxTotalScore.setText("/ " + thisQuiz.getMaxScoreUntilRound(thisRoundNr));
                tvNrOfTeams.setText("/ " + thisQuiz.getTeams().size());
                tvNrOfTeams2.setText("/ " + thisQuiz.getTeams().size());

                if (thisQuiz.getHideTopRows() > yourRankAfterThisRound)
                {
                    //tvYourScoreTotal.setText("Top " + thisQuiz.getHideTopRows());
                    tvYourRankAfterThisRound.setVisibility(View.GONE); //Text("Top " + thisQuiz.getHideTopRows() + "!!!");
                    tvNrOfTeams2.setVisibility(View.GONE);//Text(" ");
                    ivTopScore.setVisibility(View.VISIBLE);
                    //tvYourScoreForThisRnd.setText("Top " + thisQuiz.getHideTopRows());
                    //tvYourRankForThisRnd.setText("Top " + thisQuiz.getHideTopRows());
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
        tvYourRankForThisRnd = v.findViewById(R.id.tvYourRankForThisRnd);
        tvYourScoreTotal = v.findViewById(R.id.tvYourScoreTotal);
        tvYourRankAfterThisRound = v.findViewById(R.id.tvYourRankTotal);
        tvMaxRndScore = v.findViewById(R.id.tvMaxRndScore);
        tvMaxTotalScore = v.findViewById(R.id.tvMaxTotalScore);
        tvNrOfTeams = v.findViewById(R.id.tvNrOfTeams);
        tvNrOfTeams2 = v.findViewById(R.id.tvNrOfTeams2);
        ivTopScore=v.findViewById(R.id.ivTopScore);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callingActivity=(HasShowRoundScore) context;
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
