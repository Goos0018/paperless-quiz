package com.example.paperlessquiz.question;

import com.example.paperlessquiz.adapters.QuestionsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;
import java.util.List;

public class GetQuestionsLPL implements ListParsedListener<Question> {

    //private QuestionsAdapter roundQuestionsAdapter;
    private ArrayList<ArrayList<Question>> allQuestionsPerRound;
    private ArrayList<Question> allQuestions;
    //private ArrayList<Round>
    //private QuestionsList questionsList;
    //private String answer1;


    public ArrayList<ArrayList<Question>> getAllQuestionsPerRound() {
        return allQuestionsPerRound;
    }

    public GetQuestionsLPL(){
    //public GetQuestionsLPL(QuestionsAdapter roundQuestionsAdapter){
        //public GetQuestionsLPL(QuestionsAdapter roundQuestionsAdapter, QuestionsList questionsList, String answer1){
        //this.roundQuestionsAdapter=roundQuestionsAdapter;
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
            int rndId = q.getRoundID();
            int qId = q.getId();
            //add (empty questionslists until you are sue you have one at position rndId - 1
            while (allQuestionsPerRound.size() < rndId)
            {
                allQuestionsPerRound.add(new ArrayList<Question>());
            }
            //Now add questions until you are sure you have one at the position you need
            ArrayList<Question> qList = allQuestionsPerRound.get(rndId-1);
            while (qList.size() < qId)
            {
                qList.add(new Question());
            }
            //Replace the (blank) question at the correct position with the one yo have here
            qList.set(qId-1,q);
        }
        allQuestions = (ArrayList)list;
    }

}
