package com.example.paperlessquiz.spinners;

import android.widget.Spinner;
import android.widget.TextView;

import java.lang.annotation.Target;
import java.util.ArrayList;

public class BasicSpinner {
    public ArrayList<SpinnerData> arrayList;
    public int curPosition;
    public TextView tvMain;
    public TextView tvSub;


    public BasicSpinner(ArrayList<SpinnerData> arrayList, TextView tvMain, TextView tvSub, TargetObject targetObject) {
        this.arrayList = arrayList;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.curPosition=0;
    }

    public void positionChanged(){
        tvMain.setText(arrayList.get(curPosition).getName());
        tvSub.setText(arrayList.get(curPosition).getDescription());
    }

    public void moveUp(){
        if(curPosition == arrayList.size()){
         curPosition=0;
        }
        else {
            curPosition++;
        }
    }
    public void moveDown(int index){
        if(curPosition == 0){
            curPosition=arrayList.size();
        }
        else {
            curPosition--;
        }
    }
}
