package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paperlessquiz.google.adapter.GoogleAccess;
import com.example.paperlessquiz.google.adapter.LoadingListenerImpl;
import com.example.paperlessquiz.quiz.CreateQuizObjectLPL;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizParser;
import com.example.paperlessquiz.quizbasics.QuizBasics;


public class LogInToQuizAsParticipant extends AppCompatActivity {
    String quizSheetID;
    String sheetName = "QuizData";
    Quiz thisQuiz = new Quiz();
    QuizBasics thisQuizBasics;

    // TODO: pass quiz docID and load data from the real quiz (including logins and teams
    // TODO: update layout to be able to select a team or role and actually log in if the quiz allow this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_to_quiz_as_participant);
        //String quizTitle = getIntent().getStringExtra("QuizTitle");

        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra("ThisQuizGetter");
        quizSheetID= thisQuizBasics.getSheetDocID();
        String scriptParams = "DocID=" + quizSheetID + "&Sheet=" + sheetName + "&action=getdata";

        TextView textView = findViewById(R.id.textView) ;
        TextView textView2 = findViewById(R.id.textView2) ;
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(thisQuiz.getDescription());
            }
        });
        textView2.setText("About to load data for " + thisQuizBasics.getName() + " from sheet " + thisQuizBasics.getSheetDocID());
        GoogleAccess<Quiz> googleAccess = new GoogleAccess<Quiz>(this, scriptParams);
        googleAccess.getItems(new QuizParser(), new CreateQuizObjectLPL(thisQuiz),
                new LoadingListenerImpl(this, "Please wait", "Loading quiz", "Something went wrong: "));
        textView2.setText("Loaded data for " + thisQuiz.getDescription() + " from sheet " + thisQuizBasics.getSheetDocID());
    }
}
