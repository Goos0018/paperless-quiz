package com.paperlessquiz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.paperlessquiz.adapters.EditTeamsAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * Home screen for receptionist. Allows setting team status, change team names and buy or refund bonnekes
 */
public class C_ReceptionistHome extends MyActivity implements LoadingActivity {
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    EditTeamsAdapter editTeamsAdapter;
    boolean usersLoaded, bonnekesBought;
    QuizLoader quizLoader = new QuizLoader(this);

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
            //editTeamsAdapter.setTeams(thisQuiz.getTeams());
            editTeamsAdapter.notifyDataSetChanged();
        }
        //If bonnekes are bought, reload the users to show the correct amount
        if (bonnekesBought){
            bonnekesBought=false;
            if (quizLoader.buyBonnekesRequest.isRequestOK()){
                quizLoader.loadUsers();
            }
            else
            {
                Toast.makeText(this, "Error: " + quizLoader.buyBonnekesRequest.getExplanation(), Toast.LENGTH_LONG).show();
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
        editTeamsAdapter = new EditTeamsAdapter(this, thisQuiz.getTeams(), quizLoader);
        rvTeams.setAdapter(editTeamsAdapter);
    }
}
