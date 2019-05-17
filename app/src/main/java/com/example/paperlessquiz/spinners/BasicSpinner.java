package com.example.paperlessquiz.spinners;

import android.widget.TextView;

import java.util.ArrayList;

public class BasicSpinner {
    public ArrayList<SpinnerData> arrayList;
    public int newPosition;
    public int oldPosition;
    public TextView tvMain;
    public TextView tvSub;


    public BasicSpinner(ArrayList<SpinnerData> arrayList, TextView tvMain, TextView tvSub) {
        this.arrayList = arrayList;
        this.tvMain = tvMain;
        this.tvSub = tvSub;
        this.newPosition =0;
        this.oldPosition = 0;
    }

    public void positionChanged(){
        tvMain.setText(arrayList.get(newPosition).getName());
        tvSub.setText(arrayList.get(newPosition).getDescription());
    }

    public void moveUp(){
        if(newPosition == arrayList.size()){
         newPosition =0;
        }
        else {
            newPosition++;
        }
    }
    public void moveDown(int index){
        if(newPosition == 0){
            newPosition =arrayList.size();
        }
        else {
            newPosition--;
        }
    }

    public void setArrayList(ArrayList<SpinnerData> arrayList) {
        this.arrayList = arrayList;
    }
}
