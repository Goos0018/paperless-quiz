package com.example.paperlessquiz.corrections;

import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This LPL receives an array of CorrectionsLists (one for each question in the entire Quiz)
 * We structure them here per round in the allCorrectionsPerRound variable.
 */
public class GetCorrectionsListLPL implements ListParsedListener<CorrectionsList> {

    private ArrayList<ArrayList<CorrectionsList>> allCorrectionsPerRound;

    public GetCorrectionsListLPL() {
        allCorrectionsPerRound = new ArrayList<>();
    }

    public ArrayList<ArrayList<CorrectionsList>> getAllCorrectionsPerRound() {
        return allCorrectionsPerRound;
    }

    public void listParsed(List<CorrectionsList> list) {
        //List contains a list of all questions in the quiz with a QuestionID and a RoundID.
        //Loop over each element in the list and add them to Round<RoundID>[] in the order in which they are found
        for (int i = 0;i< list.size();i++)
        {
            CorrectionsList correctionsList = list.get(i);
            int rndNr = correctionsList.getRoundNr();
            int qNr = correctionsList.getQuestionNr();
            //add (empty questionslists until you are sure you have one at the required position (rndNr - 1)
            while (allCorrectionsPerRound.size() < rndNr)
            {
                allCorrectionsPerRound.add(new ArrayList<CorrectionsList>());
            }
            //Add empty Corrections until you are sure you have one at the requested position
            while (allCorrectionsPerRound.get(rndNr -1).size() < qNr)
            {
                allCorrectionsPerRound.get(rndNr -1).add(new CorrectionsList("",0,0));
            }
            //Now we are sure that this will work
            allCorrectionsPerRound.get(rndNr -1).set(qNr-1,correctionsList);
        }
    }

}
