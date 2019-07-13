package com.paperlessquiz;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.paperlessquiz.R;
import com.paperlessquiz.adapters.EditTeamsAdapter;
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.quiz.QuizLoader;

/**
 * Home screen for receptionist. Allows setting team status and change team names.
 * Actions: Refresh/Dorst/Upload
 */


public class C_ReceptionistHome extends MyActivity implements LoadingActivity {


    //TODO: create own action bar = default
    // TODO:make field editable when clicked only via below methods:
        //<your_editText>.setEnabled(true)
        //<your_editText>.requestFocus();

    //Quiz thisQuiz = MyApplication.theQuiz;
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    EditTeamsAdapter editTeamsAdapter;

    @Override
    public void loadingComplete(int requestID) {
        //Do something when loading is complete
        if (editTeamsAdapter != null) {
            editTeamsAdapter.notifyDataSetChanged();
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
            case R.id.upload:
                thisQuiz.updateTeams(C_ReceptionistHome.this);
                break;

            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_ReceptionistHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadTeams();
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
