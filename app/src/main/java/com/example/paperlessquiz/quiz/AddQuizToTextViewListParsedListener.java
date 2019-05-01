package com.example.paperlessquiz.quiz;

import android.widget.TextView;

import com.example.paperlessquiz.google.adapter.ListParsedListener;

import java.util.List;

public class AddQuizToTextViewListParsedListener implements ListParsedListener<Quiz> {

    private TextView textView;

    public AddQuizToTextViewListParsedListener(TextView textView) {
        this.textView  = textView;
    }

    public void listParsed(List<Quiz> list) {
        textView.setText("loaded: " + list.get(0).getTitle());
    }
}
