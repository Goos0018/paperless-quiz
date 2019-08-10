package com.paperlessquiz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.paperlessquiz.adapters.EditTeamsAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * Home screen for receptionist. Allows setting team status, change team names and buy or refund bonnekes
 * Actions: Refresh/Dorst/Upload
 */


public class C_ReceptionistHome extends MyActivity implements LoadingActivity {


    //TODO: create own action bar = default
    // TODO:make field editable when clicked only via below methods:
        //<your_editText>.setEnabled(true)
        //<your_editText>.requestFocus();

    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    EditTeamsAdapter editTeamsAdapter;
    boolean usersLoaded, bonnekesBought;

    @Override
    public void loadingComplete(int requestID) {
        switch (requestID) {
            case QuizDatabase.REQUEST_ID_GET_USERS:
                usersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_BUYBONNEKES:
                bonnekesBought = true;
                break;
        }
        //if users are loaded, update them
        if (usersLoaded) {
            //reset the loading statuses
            usersLoaded = false;
            quizLoader.updateTeams();
            quizLoader.updateAnswersSubmittedIntoQuiz();
            if (showTeamsAdapter != null) {
                showTeamsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.receptionist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_ReceptionistHome.this);
                quizLoader.loadUsers();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_receptionist_home);
        //Set the action bar
        setActionBarIcon();
        setActionBarTitle();

        rvTeams = findViewById(R.id.rvShowTeams);
        rvTeams.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvTeams.setLayoutManager(layoutManager);
        editTeamsAdapter = new EditTeamsAdapter(this, thisQuiz.getTeams());
        rvTeams.setAdapter(editTeamsAdapter);
    }
}
