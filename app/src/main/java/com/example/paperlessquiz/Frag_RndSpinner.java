package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.round.Round;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_RndSpinner extends Fragment {

    private Quiz thisQuiz = MyApplication.theQuiz;
    private int roundNr;
    private int oldRoundNr;
    private TextView tvRoundName,tvRoundDescription;
    ImageView ivRoundStatusL,ivRoundStatusR;
    Button btnRndUp, btnRndDown;
    HasRoundSpinner activity;

    public interface HasRoundSpinner {
        public void onRoundChanged(int roundNr);
    }

    public Frag_RndSpinner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_rnd_spinner, container, false);
        tvRoundName = v.findViewById(R.id.tvRoundName);
        tvRoundDescription = v.findViewById(R.id.tvRoundDescription);
        ivRoundStatusL=v.findViewById(R.id.ivRndStatusL);
        ivRoundStatusR=v.findViewById(R.id.ivRndStatusR);
        btnRndDown = v.findViewById(R.id.btnRndDown);
        btnRndUp = v.findViewById(R.id.btnRndUp);

        btnRndDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveDown();
            }
        });

        btnRndUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveUp();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (HasRoundSpinner) context;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        moveTo(1);
    }

    public void positionChanged() {
        tvRoundName.setText(thisQuiz.getRound(roundNr).getName());
        tvRoundDescription.setText(thisQuiz.getRound(roundNr).getDescription());
        Round thisRound = thisQuiz.getRound(roundNr);
        String thisLoginEntityType = thisQuiz.getMyLoginentity().getType();
        //Set the icon that shows the round status
        if (!thisRound.getAcceptsAnswers() && !thisRound.getAcceptsCorrections() && !thisRound.isCorrected()) {
            ivRoundStatusL.setImageResource(R.drawable.rnd_not_yet_open);
            ivRoundStatusR.setImageResource(R.drawable.rnd_not_yet_open);
        }
        if (thisRound.getAcceptsAnswers()) {
            ivRoundStatusL.setImageResource(R.drawable.rnd_open);
            ivRoundStatusR.setImageResource(R.drawable.rnd_open);
        }
        if (thisRound.getAcceptsCorrections()) {
            ivRoundStatusL.setImageResource(R.drawable.rnd_closed);
            ivRoundStatusR.setImageResource(R.drawable.rnd_closed);
        }
        if (thisRound.isCorrected()) {
            ivRoundStatusL.setImageResource(R.drawable.rnd_corrected);
            ivRoundStatusR.setImageResource(R.drawable.rnd_corrected);
        }
        activity.onRoundChanged(roundNr);
    }

    public void moveTo(int newRoundNr) {
        oldRoundNr = roundNr;
        if (newRoundNr <= thisQuiz.getRounds().size()) {
            roundNr = newRoundNr;
        } else {
            roundNr = 1;
        }
        positionChanged();
    }

    public void moveUp() {
        oldRoundNr = roundNr;
        if (roundNr == thisQuiz.getRounds().size()) {
            roundNr = 1;
        } else {
            roundNr++;
        }
        positionChanged();
    }

    public void moveDown() {
        oldRoundNr = roundNr;
        if (roundNr == 1) {
            roundNr = thisQuiz.getRounds().size();
        } else {
            roundNr--;
        }
        positionChanged();
    }

    public int getRoundNr() {
        return roundNr;
    }

}
