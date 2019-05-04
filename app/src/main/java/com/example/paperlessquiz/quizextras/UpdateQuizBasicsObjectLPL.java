package com.example.paperlessquiz.quizextras;

import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quizbasics.QuizBasics;

import java.util.List;

public class UpdateQuizBasicsObjectLPL implements ListParsedListener<QuizExtras> {

    private QuizExtras quizExtras;

    public UpdateQuizBasicsObjectLPL(QuizExtras quizExtras) {
        this.quizExtras = quizExtras;

    }

    public void listParsed(List<QuizExtras> list) {
        quizExtras = new QuizExtras(list.get(0));

    }
}
