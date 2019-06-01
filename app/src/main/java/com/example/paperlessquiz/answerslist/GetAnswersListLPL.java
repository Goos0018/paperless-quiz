package com.example.paperlessquiz.answerslist;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This LPL receives an array of Answerslists (one for each question in the entire Quiz)
 * We structure them here per round in the allAnswersPerRound variable.
 */
public class GetAnswersListLPL implements ListParsedListener<AnswersList> {

    private ArrayList<ArrayList<AnswersList>> allAnswersPerRound;

    public GetAnswersListLPL() {
        allAnswersPerRound = new ArrayList<>();
    }

    public ArrayList<ArrayList<AnswersList>> getAllAnswersPerRound() {
       return allAnswersPerRound;
    }

    public void listParsed(List<AnswersList> list) {
        //List contains a list of all questions in the quiz with a QuestionID and a RoundID.
        //Loop over each element in the list and add them to Round<RoundID>[] in the order in which they are found
        for (int i = 0;i< list.size();i++)
        {
            AnswersList answersList = list.get(i);
            int rndNr = answersList.getRoundNr();
            int qNr = answersList.getQuestionNr();
            //add (empty questionslists until you are sure you have one at the required position (rndNr - 1)
            while (allAnswersPerRound.size() < rndNr)
            {
                allAnswersPerRound.add(new ArrayList<AnswersList>());
            }
            //Add empty questions until you are sure you have one at the requested position
            while (allAnswersPerRound.get(rndNr -1).size() < qNr)
            {
                allAnswersPerRound.get(rndNr -1).add(new AnswersList("",0,0));
            }
            //Now we are sure that this will work
            allAnswersPerRound.get(rndNr -1).set(qNr-1,answersList);
        }
    }

}
