package com.example.paperlessquiz.quizdetails;

import android.widget.TextView;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.List;

public class AddQuizToTextViewListParsedListener implements ListParsedListener<QuizDetails> {

    private TextView textView;

    public AddQuizToTextViewListParsedListener(TextView textView) {
        this.textView  = textView;
    }

    public void listParsed(List<QuizDetails> list) {
        textView.setText("loaded: quiz");
    }
}
