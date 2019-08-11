package com.paperlessquiz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.paperlessquiz.adapters.ShowTeamsAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * Quizmaster home screen. Show the teams and whether or not they submitted answers for a particular round. Also allows toggling round statuses
 * Actions: Refresh/Dorst/Rounds/Upload
 */
public class C_QuizmasterHome extends MyActivity implements FragRoundSpinner.HasRoundSpinner, LoadingActivity {
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    ShowTeamsAdapter showTeamsAdapter;
    int roundNr;
    QuizLoader quizLoader;
    boolean usersLoaded, answersSubmittedLoaded;

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
            quizLoader.updateUsersIntoQuiz();
            quizLoader.updateAnswersSubmittedIntoQuiz();
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
/*
    public void showHideTop(){
        Toast toast = Toast.makeText(this, "Top " + thisQuiz.getHideTopRows(), Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setBackgroundColor(Color.RED);
        toast.show();
    }
*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                //QuizLoader quizLoader = new QuizLoader(C_QuizmasterHome.this);
                quizLoader.loadUsers();
                quizLoader.loadAnswersSubmitted();
                break;
                /*
            case R.id.plus1:
                //QuizLoader quizLoader = new QuizLoader(C_QuizmasterHome.this);
                thisQuiz.setHideTopRows(thisQuiz.getHideTopRows()+1);
                showHideTop();
                break;
            case R.id.minus1:
                //QuizLoader quizLoader = new QuizLoader(C_QuizmasterHome.this);
                //TODO: upload this to the Quiz and include in quizlist data?
                thisQuiz.setHideTopRows(thisQuiz.getHideTopRows()-1);
                showHideTop();
                break;
                */
            /*
            case R.id.rounds:
                Intent intent = new Intent(C_QuizmasterHome.this, C_QuizmasterRounds.class);
                startActivity(intent);
                case R.id.upload:
                //thisQuiz.updateUsersIntoQuiz(C_QuizmasterHome.this);
                break;
            */
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
        showTeamsAdapter = new ShowTeamsAdapter(this, thisQuiz.getTeams(), roundNr);
        rvTeams.setAdapter(showTeamsAdapter);
        quizLoader = new QuizLoader(this);
        quizLoader.loadUsers();
        quizLoader.loadAnswersSubmitted();
    }
}
