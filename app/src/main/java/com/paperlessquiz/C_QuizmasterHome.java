package com.paperlessquiz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.paperlessquiz.adapters.ShowTeamsAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

import java.util.ArrayList;

/**
 * Quizmaster home screen. Show the teams and relevant stats about the answers they submitted (either nr of answers when round is open, or nr of corrected answers when round
 * is being corrected. Also allows toggling round statuses.
 */
public class C_QuizmasterHome extends MyActivity implements FragRoundSpinner.HasRoundSpinner, LoadingActivity {
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    ShowTeamsAdapter showTeamsAdapter;
    int roundNr=1;
    QuizLoader quizLoader;
    boolean usersLoaded, roundsLoaded, answerStatsLoaded;
    //ArrayList<Integer> nrOfAnswers;

    @Override
    public void onRoundChanged(int oldRoundnNr, int roundNr) {
        this.roundNr = roundNr;
        if (showTeamsAdapter != null) {
            showTeamsAdapter.setRoundNr(roundNr);
            showTeamsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadingComplete(int requestID) {
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_USERS:
                usersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ROUNDS:
                roundsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ANSWERSTATS:
                answerStatsLoaded = true;
                break;
        }
        //If everything is properly loaded, we can start populating the central Quiz object
        if (usersLoaded && roundsLoaded && answerStatsLoaded) {
            //reset the loading statuses
            usersLoaded = false;
            roundsLoaded=false;
            answerStatsLoaded = false;
            quizLoader.updateUsersIntoQuiz();
            quizLoader.updateRoundsIntoQuiz();
            showTeamsAdapter.setNrOfAnswers(quizLoader.getAnswerStatsRequest.getResultsList());
            if (showTeamsAdapter != null) {
                showTeamsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quizmaster, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void refreshData() {
        usersLoaded = false;
        roundsLoaded=false;
        answerStatsLoaded = false;
        quizLoader.loadUsers();
        quizLoader.loadRounds();
        switch (thisQuiz.getRound(roundNr).getRoundStatus()) {
            case QuizDatabase.ROUNDSTATUS_CLOSED:
                quizLoader.loadAnswerStats(roundNr, QuizDatabase.ANSWERSTAT_COUNTBLANK);
                break;
            case QuizDatabase.ROUNDSTATUS_OPENFORANSWERS:
                quizLoader.loadAnswerStats(roundNr, QuizDatabase.ANSWERSTAT_COUNTNONBLANK);
                break;
            default:
                quizLoader.loadAnswerStats(roundNr, QuizDatabase.ANSWERSTAT_COUNTCORRECTED);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                refreshData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_quizmaster_home);
        //Set the action bar
        setActionBarIcon();
        setActionBarTitle();
        rvTeams = findViewById(R.id.rvShowTeams);
        rvTeams.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvTeams.setLayoutManager(layoutManager);
        showTeamsAdapter = new ShowTeamsAdapter(this, thisQuiz.getTeams(), roundNr, new ArrayList<Integer>());
        rvTeams.setAdapter(showTeamsAdapter);
        quizLoader = new QuizLoader(this);
        refreshData();
    }
}
