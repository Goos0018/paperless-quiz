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
public class FragRoundSpinner extends Fragment {

    private Quiz thisQuiz = MyApplication.theQuiz;
    private int position = 1;
    private int oldPosition;
    private TextView tvRoundName,tvRoundDescription;
    ImageView ivRoundStatusL,ivRoundStatusR;
    Button btnRndUp, btnRndDown;
    HasRoundSpinner activity;

    public interface HasRoundSpinner {
        public void onRoundChanged(int oldRoundNr, int roundNr);
    }

    public FragRoundSpinner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_round_spinner, container, false);
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
        tvRoundName.setText(thisQuiz.getRound(position).getName());
        tvRoundDescription.setText(thisQuiz.getRound(position).getDescription());
        refreshIcons();
        /*Round thisRound = thisQuiz.getRound(position);
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
        */
        activity.onRoundChanged(oldPosition, position);
    }

    public void refreshIcons(){
        //Set the icon that shows the round status
        Round thisRound = thisQuiz.getRound(position);
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
    }

    public void moveTo(int newRoundNr) {
        oldPosition = position;
        if (newRoundNr <= thisQuiz.getRounds().size()) {
            position = newRoundNr;
        } else {
            position = 1;
        }
        positionChanged();
    }

    public void moveUp() {
        oldPosition = position;
        if (position == thisQuiz.getRounds().size()) {
            position = 1;
        } else {
            position++;
        }
        positionChanged();
    }

    public void moveDown() {
        oldPosition = position;
        if (position == 1) {
            position = thisQuiz.getRounds().size();
        } else {
            position--;
        }
        positionChanged();
    }

    public int getPosition() {
        return position;
    }

}
