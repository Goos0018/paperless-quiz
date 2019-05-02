package com.example.paperlessquiz.quiz;

import com.example.paperlessquiz.google.adapter.ListParsedListener;

import java.util.List;

public class CreateQuizObjectLPL implements ListParsedListener<Quiz> {

    private Quiz quiz;

    public CreateQuizObjectLPL(Quiz quiz) {
        this.quiz=quiz;

    }

    public void listParsed(List<Quiz> list) {
        quiz.setDescription(list.get(0).getName());

    }
}
