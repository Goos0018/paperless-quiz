package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.paperlessquiz.google.adapter.GoogleAccess;
import com.example.paperlessquiz.google.adapter.LoadingListenerImpl;
import com.example.paperlessquiz.quiz.AddQuizToTextViewListParsedListener;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizParser;


public class LogInToQuiz extends AppCompatActivity {
    String quizSheetID = "1LR6F-VJCNPFAhgibNspJJBRCDW_mlCz_PFkepSZYxx8";
    String sheetName = "QuizData";
    String scriptParams = "DocID=" + quizSheetID + "&Sheet=" + sheetName + "&action=getdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_to_quiz);
        //String quizTitle = getIntent().getStringExtra("QuizTitle");

        TextView textView = findViewById(R.id.textView) ;
        TextView textView2 = findViewById(R.id.textView2) ;
        textView2.setText("Quiz that was clicked: " + getIntent().getStringExtra("QuizTitle"));
        GoogleAccess<Quiz> googleAccess = new GoogleAccess<Quiz>(this, scriptParams);
        googleAccess.getItems(new QuizParser(), new AddQuizToTextViewListParsedListener(textView),
                new LoadingListenerImpl(this, "Please wait", "Loading quiz", "Something went wrong: "));

    }
}
