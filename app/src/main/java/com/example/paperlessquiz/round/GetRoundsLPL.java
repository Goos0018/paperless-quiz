package com.example.paperlessquiz.round;

import com.example.paperlessquiz.MyApplication;
import com.example.paperlessquiz.google.access.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This LPL receives an ArrayList of Rounds, we don't need to do anything with this list, just return it
 * However, because we need to refresh rounds information during  the quiz as well, we also update the central Quiz object with the information retrieved here
 */
public class GetRoundsLPL implements ListParsedListener<Round> {

    private ArrayList<Round> rounds;

    public GetRoundsLPL() {
        rounds = new ArrayList<Round>();
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void listParsed(List<Round> list) {
        //rounds is the list of rounds with only the information from the Rounds sheet
        //we copy the information here to the central Quiz object
        rounds = (ArrayList) list;
        for (int i = 0; i < rounds.size(); i++) {
            Round rnd = list.get(i);
            int rndNr = rnd.getRoundNr();
            //Update MyApplication.theQuiz with the new info
            if (rndNr <= MyApplication.theQuiz.getRounds().size()) {
                MyApplication.theQuiz.getRound(rndNr).UpdateRoundBasics(rnd);
            }
            else{
            }
        }
    }
}
