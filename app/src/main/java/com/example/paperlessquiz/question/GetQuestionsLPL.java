package com.example.paperlessquiz.question;

import com.example.paperlessquiz.adapters.RoundQuestionsAdapter;
import com.example.paperlessquiz.google.access.ListParsedListener;
import com.example.paperlessquiz.round.Round;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetQuestionsLPL implements ListParsedListener<Question> {

    //private RoundQuestionsAdapter roundQuestionsAdapter;
    private ArrayList<QuestionsList> allQuestionsPerRound;
    //private ArrayList<Round>
    //private QuestionsList questionsList;
    //private String answer1;


    public ArrayList<QuestionsList> getAllQuestionsPerRound() {
        return allQuestionsPerRound;
    }

    public GetQuestionsLPL(){
    //public GetQuestionsLPL(RoundQuestionsAdapter roundQuestionsAdapter){
        //public GetQuestionsLPL(RoundQuestionsAdapter roundQuestionsAdapter, QuestionsList questionsList, String answer1){
        //this.roundQuestionsAdapter=roundQuestionsAdapter;
        this.allQuestionsPerRound=new ArrayList<QuestionsList>();
    }
/*
    public QuestionsList getQuestionsList() {
        return questionsList;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }
*/
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
                allQuestionsPerRound.add(new QuestionsList());
            }
            //add (empty) questions to questionslist(rndId) until you are sure you have one at position qId - -2
            while (allQuestionsPerRound.get(rndId-1).getQuestionsList().size() < qId-1)
            {
                allQuestionsPerRound.get(rndId-1).addQuestion(new Question());
            }
            //Now we are sure we can do this: question will be added in round rndId on poistion qId (taking into account array indices start at 0)
            allQuestionsPerRound.get(rndId-1).addQuestion(qId-1,q);
            /*add the questionslist for rnd if it does not exist already
            if (!(allQuestionsPerRound.containsKey(rnd)))
            {
                allQuestionsPerRound.put(rnd,new QuestionsList());
            }
            allQuestionsPerRound.get(rnd).addQuestion(q);
            */
        }
        //roundQuestionsAdapter.addAll(list);
    }
}
