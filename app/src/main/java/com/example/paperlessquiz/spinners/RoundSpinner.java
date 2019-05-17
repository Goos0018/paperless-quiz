package com.example.paperlessquiz.spinners;

import android.widget.TextView;

import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;

public class RoundSpinner extends BasicSpinner {
    public ArrayList<Round> rounds;

    public RoundSpinner(ArrayList<SpinnerData> arrayList, TextView tvMain, TextView tvSub, TargetObject targetObject, ArrayList<Round> rounds1) {
        super(arrayList, tvMain, tvSub, targetObject);
        this.rounds = rounds1;
    }

    @Override
    public void positionChanged() {
        super.positionChanged();

    }

}
