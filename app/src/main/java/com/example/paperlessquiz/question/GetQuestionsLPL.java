package com.example.paperlessquiz.question;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;
/**
 * This LPL receives an array of Questions (one for each question in the entire Quiz)
 * We structure them here per round in the allQuestionsPerRound variable. We do not load them in the Quiz object, loading questions should be done only once, when the app starts
 */
public class GetQuestionsLPL implements ListParsedListener<Question> {

    private ArrayList<ArrayList<Question>> allQuestionsPerRound;

    public ArrayList<ArrayList<Question>> getAllQuestionsPerRound() {
        return allQuestionsPerRound;
    }

    public GetQuestionsLPL(){
        this.allQuestionsPerRound=new ArrayList<ArrayList<Question>>();
    }
    @Override
    public void listParsed(List<Question> list)
    {
        //List contains a list of all questions in the quiz with a QuestionID and a RoundID.
        //Loop over each element in the list and add them to Round<RoundID>[] in the order in which they are found
        for (int i = 0;i< list.size();i++)
        {
            Question q = list.get(i);
            int rndNumber = q.getRoundNr();
            int qNumber = q.getQuestionNr();
            //add (empty) questionslists until you are sue you have one at position rndNumber
            while (allQuestionsPerRound.size() < rndNumber)
            {
                allQuestionsPerRound.add(new ArrayList<Question>());
            }
            ArrayList<Question> qList = allQuestionsPerRound.get(rndNumber-1);
            //Now add questions until you are sure you have one at the position you need
            while (qList.size() < qNumber)
            {
                qList.add(new Question());
            }
            //Replace the (blank) question at the correct position with the one you have here
            qList.set(qNumber-1,q);
        }
    }
}
