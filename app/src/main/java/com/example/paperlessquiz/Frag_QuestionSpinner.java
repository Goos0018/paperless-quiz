package com.example.paperlessquiz;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.quiz.Quiz;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_QuestionSpinner extends Fragment {

    private Quiz thisQuiz = MyApplication.theQuiz;
    private int questionNr = 1, oldQuestionNr, roundNr;
    TextView tvQuestionName, tvQuestionDescription;
    Button btnQuestionDown, btnQuestionUp;
    QuestionHasChanged activity;

    public interface QuestionHasChanged {
        public void onQuestionChanged(int oldQuestionNr, int newQuestionNr);
    }

    public Frag_QuestionSpinner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_question_spinner, container, false);
        tvQuestionName = v.findViewById(R.id.tvQuestionName);
        tvQuestionDescription = v.findViewById(R.id.tvQuestionDescription);
        btnQuestionDown = v.findViewById(R.id.btnQuestionDown);
        btnQuestionUp = v.findViewById(R.id.btnQuestionUp);
        btnQuestionDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveDown();
            }
        });
        btnQuestionUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveUp();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshDisplay();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (QuestionHasChanged) context;
    }

    public void refreshDisplay() {
        tvQuestionName.setText(thisQuiz.getQuestion(roundNr, questionNr).getName());
        tvQuestionDescription.setText(thisQuiz.getQuestion(roundNr, questionNr).getDescription());
        activity.onQuestionChanged(0,questionNr);
    }

    public void moveTo(int newQuestionNr) {
        oldQuestionNr = questionNr;
        if (newQuestionNr <= thisQuiz.getRound(roundNr).getQuestions().size()) {
            questionNr = newQuestionNr;
        } else {
            questionNr = 1;
        }
        refreshDisplay();
    }

    public void moveUp() {
        oldQuestionNr = questionNr;
        if (questionNr == thisQuiz.getRound(roundNr).getQuestions().size()) {
            questionNr = 1;
        } else {
            questionNr++;
        }
        refreshDisplay();
    }

    public void moveDown() {
        oldQuestionNr = questionNr;
        if (questionNr == 1) {
            questionNr = thisQuiz.getRound(roundNr).getQuestions().size();
        } else {
            questionNr--;
        }
        refreshDisplay();
    }

    public void setRoundNr(int roundNr) {
        this.roundNr = roundNr;
        refreshDisplay();
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public int getOldQuestionNr() {
        return oldQuestionNr;
    }
}
