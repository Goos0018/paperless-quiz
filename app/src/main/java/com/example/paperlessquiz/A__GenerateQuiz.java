package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.EditTeamsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.google.access.LoadingListenerShowProgress;
import com.example.paperlessquiz.quiz.QuizGenerator;

import java.util.ArrayList;

public class A__GenerateQuiz extends AppCompatActivity implements FragSpinner.HasSpinner, LoadingActivity {

    int nrOfRounds = 1, nrOfTeams = 1;
    String quizName;
    ArrayList<Integer> roundNrOfQuestions = new ArrayList<>();
    QuizGenerator generator;
    EditText etQuizName, etNrOfRounds, etNrOfQuestions, etNrOfTeams;
    TextView tvDisplayQuiz;


    Button btnInitialize, btnGenerateQuiz;

    @Override
    public void loadingComplete() {
        generator.setDocID(generator.googleAccessCreateQuiz.getResult());
        generator.setAllHeaders();
        generator.initializeAllSheets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_a__generate_quiz);
        etQuizName = findViewById(R.id.etQuizName);
        etNrOfRounds = findViewById(R.id.etNrOfRounds);
        etNrOfQuestions = findViewById(R.id.etNrOfQuestions);
        etNrOfTeams = findViewById(R.id.etNrOfTeams);
        tvDisplayQuiz = findViewById(R.id.tvDisplayQuiz);
        btnInitialize = findViewById(R.id.btnInitGenerator);
        btnGenerateQuiz = findViewById(R.id.btnGenerateQuiz);

        btnInitialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizName = etQuizName.getText().toString().trim();
                nrOfRounds = Integer.valueOf(etNrOfRounds.getText().toString().trim());
                nrOfTeams = Integer.valueOf(etNrOfTeams.getText().toString().trim());
                for (int i = 0; i < nrOfRounds; i++) {
                    roundNrOfQuestions.add(1);
                }
                displayQuizSummary();
            }
        });

        btnGenerateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generator = new QuizGenerator(A__GenerateQuiz.this, quizName, nrOfRounds, roundNrOfQuestions, nrOfTeams);
                generator.createQuiz();
            }
        });
    }

    public void displayQuizSummary() {
        String tmp = "Quiz name: " + quizName + "\n" +
                "Nr of rounds: " + nrOfRounds + "\n" +
                "Nr of teams: " + nrOfTeams + "\n";
        for (int i = 0; i < nrOfRounds; i++) {
            tmp = tmp + "Nr of Questions for round " + (i + 1) + ": " + roundNrOfQuestions.get(i) + "\n";
        }
        tvDisplayQuiz.setText(tmp);
    }

    @Override
    public void onSpinnerChange(int oldPos, int newPos) {
        if (roundNrOfQuestions.size() == 0) {
        } else {
            roundNrOfQuestions.set(oldPos - 1, Integer.valueOf(etNrOfQuestions.getText().toString().trim()));
            etNrOfQuestions.setText("" + roundNrOfQuestions.get(newPos - 1));
            displayQuizSummary();
        }
    }

    @Override
    public String getValueToSetForPrimaryField(int newPos) {
        return "Round";
    }

    @Override
    public String getValueToSetForSecondaryField(int newPos) {
        return "" + newPos;
    }

    @Override
    public int getSizeOfSpinnerArray() {
        return nrOfRounds;
    }
}
