package com.example.paperlessquiz.quizextras;

import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quizbasics.QuizBasics;

import java.util.List;

public class GetQuizExtrasLPL implements ListParsedListener<QuizExtras> {

    private QuizExtras quizExtras;

    public GetQuizExtrasLPL() {
    //public GetQuizExtrasLPL(QuizExtras quizExtras) {
        this.quizExtras = new QuizExtras();

    }

    public QuizExtras getQuizExtras() {
        return quizExtras;
    }

    public void listParsed(List<QuizExtras> list) {
        quizExtras = new QuizExtras(list.get(0));

    }
}
