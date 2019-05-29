package com.example.paperlessquiz.answerslist;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.answerslist.AnswersList;
import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class GetAnswersListLPL implements ListParsedListener<AnswersList> {

    private Quiz quiz;
    private ArrayList<AnswersList> allAnswers;
    private ArrayList<ArrayList<AnswersList>> allAnswersPerRound;
    private ArrayList<ArrayList<Answer>> myAnswersPerRound;

    public GetAnswersListLPL(Quiz quiz) {
        allAnswers = new ArrayList<AnswersList>();
        allAnswersPerRound = new ArrayList<>();
        myAnswersPerRound = new ArrayList<>();
        this.quiz = quiz;

    }

    public ArrayList<AnswersList> getAnswers() {
        return allAnswers;
    }

    public void listParsed(List<AnswersList> list) {
        allAnswers = (ArrayList) list;
        //List contains a list of all questions in the quiz with a QuestionID and a RoundID.
        //Loop over each element in the list and add them to Round<RoundID>[] in the order in which they are found
        for (int i = 0;i< list.size();i++)
        {
            AnswersList answersList = list.get(i);
            int rndId = answersList.getRoundNr();
            int qID = answersList.getQuestionNr();
            //add (empty questionslists until you are sure you have one at the required position (rndId - 1)
            while (allAnswersPerRound.size() < rndId)
            {
                allAnswersPerRound.add(new ArrayList<AnswersList>());
                myAnswersPerRound.add(new ArrayList<>());
            }
            //Add empty questions until you are sure you have one at the requested position
            while (allAnswersPerRound.get(rndId-1).size() < qID)
            {
                allAnswersPerRound.get(rndId-1).add(new AnswersList("",0,0));
                myAnswersPerRound.get(rndId-1).add(new Answer(""));
            }
            //Now we are sure that this will work
            allAnswersPerRound.get(rndId-1).set(answersList.getQuestionNr()-1,answersList);
        }

        if (!(quiz == null)) {
            quiz.setAllAnswers(allAnswersPerRound);
            quiz.setMyAnswers(myAnswersPerRound);
        }
    }

}
