package com.paperlessquiz.quizlistdata;

import com.paperlessquiz.adapters.QuizListAdapter;
import com.paperlessquiz.googleaccess.ListParsedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * This LPL retrieves the listOfQuizzes of all Quizzes in the central register
 * When the listOfQuizzes is parsed, we feed it to the adapter that was passed in the constructor.
 */

public class GetQuizListDataLPL implements ListParsedListener<QuizListData> {

    private QuizListAdapter quizListAdapter;
    private ArrayList<QuizListData> listOfQuizzes;

    public GetQuizListDataLPL(QuizListAdapter quizListAdapter) {
        this.quizListAdapter = quizListAdapter;
    }

    public void listParsed(List<QuizListData> list) {
        quizListAdapter.addAll(list);
        this.listOfQuizzes = (ArrayList)list;
    }

    //@Override
    public List<QuizListData> getData() {
        return (ArrayList) this.listOfQuizzes;
    }
}
