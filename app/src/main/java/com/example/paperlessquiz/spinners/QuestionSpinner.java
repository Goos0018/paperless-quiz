package com.example.paperlessquiz.spinners;

import android.widget.EditText;
import android.widget.TextView;

import com.example.paperlessquiz.answer.Answer;

import java.util.ArrayList;

public class QuestionSpinner extends BasicSpinner {
    ArrayList<Answer> answers;
    EditText answerField;

    public QuestionSpinner(ArrayList<SpinnerData> arrayList, TextView tvMain, TextView tvSub, TargetObject targetObject, ArrayList<Answer> answers) {
        super(arrayList, tvMain, tvSub, targetObject);
        this.answers = answers;
    }

    @Override
    public void positionChanged() {
        super.positionChanged();
        answers.add(curPosition,new Answer(curPosition,answerField.getText().toString().trim(),""));
    }
}
