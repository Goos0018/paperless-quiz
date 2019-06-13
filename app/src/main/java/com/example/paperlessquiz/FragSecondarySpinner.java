package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * This Fragment represents a spinner that allows you to scroll up and down through a list of items
 * that are provided by the calling activity via the interface HasSecondarySpinner.
 */
public class FragSecondarySpinner extends Fragment {

    private int secSpinnerPos = 1, oldSecSpinnerPos, priSpinnerPos;
    TextView tvPrimaryField, tvSecondaryField;
    Button btnDown, btnUp;
    HasSecondarySpinner activity;

    public interface HasSecondarySpinner {
        //Implement stuff to do in the activity when this spinner changes
        public void onSecondarySpinnerChanged(int oldSecSpinnerNr, int newSecSpinnerNr);
        //Get the values to set in the first and second field, based on pri and sec spinner positions
        public String getValueToSetForPrimaryField(int priSpinnerPos, int secSpinnerPos);
        public String getValueToSetForSecondaryField(int priSpinnerPos, int secSpinnerPos);
        //Get the size of the Array of the values to use for priSpinnerPos
        public int getSizeOfSecSpinnerArray(int priSpinnerPos);
    }

    public FragSecondarySpinner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_secondary_spinner, container, false);
        tvPrimaryField = v.findViewById(R.id.tvPrimaryField);
        tvSecondaryField = v.findViewById(R.id.tvSecondaryField);
        btnDown = v.findViewById(R.id.btnDown);
        btnUp = v.findViewById(R.id.btnUp);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveDown();
            }
        });
        btnUp.setOnClickListener(new View.OnClickListener() {
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
        activity = (HasSecondarySpinner) context;
    }

    public void refreshDisplay() {
        tvPrimaryField.setText(activity.getValueToSetForPrimaryField(priSpinnerPos, secSpinnerPos));
        tvSecondaryField.setText(activity.getValueToSetForSecondaryField(priSpinnerPos, secSpinnerPos));
        activity.onSecondarySpinnerChanged(0, secSpinnerPos);
    }

    public void moveTo(int newSecSpinnerPos) {
        oldSecSpinnerPos = secSpinnerPos;
        if (newSecSpinnerPos <= activity.getSizeOfSecSpinnerArray(priSpinnerPos)) {
            secSpinnerPos = newSecSpinnerPos;
        } else {
            secSpinnerPos = 1;
        }
        refreshDisplay();
    }

    public void moveUp() {
        oldSecSpinnerPos = secSpinnerPos;
        if (secSpinnerPos == activity.getSizeOfSecSpinnerArray(priSpinnerPos)) {
            secSpinnerPos = 1;
        } else {
            secSpinnerPos++;
        }
        refreshDisplay();
    }

    public void moveDown() {
        oldSecSpinnerPos = secSpinnerPos;
        if (secSpinnerPos == 1) {
            secSpinnerPos = activity.getSizeOfSecSpinnerArray(priSpinnerPos);
        } else {
            secSpinnerPos--;
        }
        refreshDisplay();
    }

    public void setPrimarySpinnerPos(int newPrimarySpinnerPos) {
        this.priSpinnerPos = newPrimarySpinnerPos;
        refreshDisplay();
    }

    public int getSecSpinnerPos() {
        return secSpinnerPos;
    }

    public int getOldSecSpinnerPos() {
        return oldSecSpinnerPos;
    }


}
