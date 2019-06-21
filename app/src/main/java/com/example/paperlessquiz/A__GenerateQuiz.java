package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.google.access.LoadingListenerShowProgress;
import com.example.paperlessquiz.quiz.QuizGenerator;

import java.util.ArrayList;

public class A__GenerateQuiz extends AppCompatActivity implements LoadingActivity, FragSpinner.HasSpinner {

    int nrOfRounds = 1,nrOfTeams=1;
    ArrayList<Integer> roundNrOfQuestions = new ArrayList<>();
    QuizGenerator generator;
    GoogleAccessSet googleAccessSet;

    @Override
    public void loadingComplete() {
        String result = googleAccessSet.getResult();
    }

    Button btnGenerateQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_a__generate_quiz);
        btnGenerateQuiz = findViewById(R.id.btnGenerateQuiz);
        btnGenerateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generator = new QuizGenerator(A__GenerateQuiz.this,nrOfRounds,roundNrOfQuestions,nrOfTeams);
                googleAccessSet = new GoogleAccessSet(A__GenerateQuiz.this,"quizName=Rupert&sheetsToCreate=[\"test1\", \"test2\"]&action=createQuizDoc",10);
                googleAccessSet.setData(new LoadingListenerShowProgress(A__GenerateQuiz.this,"loading","creating quiz","Error!",true));
            }
        });
    }

    @Override
    public void onSpinnerChange(int oldPos, int newPos) {

    }

    @Override
    public String getValueToSetForPrimaryField(int newPos) {
        return null;
    }

    @Override
    public String getValueToSetForSecondaryField(int newPos) {
        return null;
    }

    @Override
    public int getSizeOfSpinnerArray() {
        return 0;
    }
}
