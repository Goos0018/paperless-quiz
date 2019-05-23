package com.example.paperlessquiz.answerslist;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.answerslist.AnswersList;
import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class GetAnswersListLPL implements ListParsedListener<AnswersList> {

    private Quiz quiz;
    private ArrayList<AnswersList> allAnswers;
    private ArrayList<ArrayList<AnswersList>> allAnswersPerRound;

    public GetAnswersListLPL(Quiz quiz) {
        allAnswers = new ArrayList<AnswersList>();
        allAnswersPerRound = new ArrayList<>();
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
            //add (empty questionslists until you are sue you have one at position rndId - 1
            while (allAnswersPerRound.size() < rndId)
            {
                allAnswersPerRound.add(new ArrayList<AnswersList>());
            }
            allAnswersPerRound.get(rndId-1).add(answersList.getQuestionNr(),answersList);
        }

        if (!(quiz == null)) {
            quiz.setAllAnswers(allAnswersPerRound);
        }
    }

}
