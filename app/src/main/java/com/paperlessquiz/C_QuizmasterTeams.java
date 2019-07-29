package com.paperlessquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.paperlessquiz.R;
import com.paperlessquiz.adapters.ShowTeamsAdapter;
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizGenerator;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * Quizmaster screen to show the teams and whether or not they submitted answers for a particular round
 * Actions: Refresh/Dorst/Rounds/Upload
 */
public class C_QuizmasterTeams extends MyActivity implements FragRoundSpinner.HasRoundSpinner, LoadingActivity {
    //Quiz thisQuiz = MyApplication.theQuiz;
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    ShowTeamsAdapter showTeamsAdapter;
    int roundNr;
    QuizLoader quizLoader;
    boolean usersLoaded,answersSubmittedLoaded;

    @Override
    public void onRoundChanged(int oldRoundnNr, int roundNr) {
        this.roundNr = roundNr;
        //if (thisQuiz.getThisUser().getType().equals(QuizGenerator.TYPE_QUIZMASTER) && showTeamsAdapter != null) {
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
            case QuizDatabase.REQUEST_ID_GET_ANSWERSSUBMITTED:
                answersSubmittedLoaded = true;
                break;
        }
        //If everything is properly loaded, we can start populating the central Quiz object
        if (usersLoaded && answersSubmittedLoaded) {
            //reset the loading statuses
            usersLoaded = false;
            answersSubmittedLoaded = false;
            quizLoader.updateTeams();
            quizLoader.updateAnswersSubmittedIntoQuiz();
            if (showTeamsAdapter != null) {
                showTeamsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quizmaster, menu);
        //Hide item for the Teams button and also the upload button
        menu.findItem(R.id.teams).setVisible(false);
        //menu.findItem(R.id.upload).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                //thisQuiz.updateTeams(C_QuizmasterTeams.this);
                break;

            case R.id.refresh:
                //QuizLoader quizLoader = new QuizLoader(C_QuizmasterTeams.this);
                quizLoader.loadUsers();
                quizLoader.loadAnswersSubmitted();
                break;

            case R.id.rounds:
                Intent intent = new Intent(C_QuizmasterTeams.this, C_QuizmasterRounds.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_quizmaster_teams);
        //Set the action bar
        setActionBarIcon();
        setActionBarTitle();
        rvTeams = findViewById(R.id.rvShowTeams);
        rvTeams.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvTeams.setLayoutManager(layoutManager);
        showTeamsAdapter = new ShowTeamsAdapter(this, thisQuiz.getTeams(), roundNr);
        rvTeams.setAdapter(showTeamsAdapter);
        quizLoader = new QuizLoader(this);
        quizLoader.loadUsers();
        quizLoader.loadAnswersSubmitted();
    }
}
