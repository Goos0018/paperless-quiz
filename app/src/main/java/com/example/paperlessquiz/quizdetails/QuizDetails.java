package com.example.paperlessquiz.quizdetails;

import com.example.paperlessquiz.Team;
import com.example.paperlessquiz.round.Round;

import java.io.Serializable;

public class QuizDetails implements Serializable {
    private Round[] rounds;
    private Team[] participants;

    public QuizDetails() {
        ;
    }




}
