package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.paperlessquiz.adapters.EditTeamsAdapter;
import com.example.paperlessquiz.adapters.ShowTeamsAdapter;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;

public class C_EditOrShowTeams extends AppCompatActivity {

    //TODO: make name field and presence editable for Receptionist + toggle presence via icon + hide round AnswersSubmitted image
    //TODO: find a way to hide actions not needed by certain roles
    //TODO: Allow Quizmaster access to this screen but no edit + round taken from previous screen + navigate back


    Quiz thisQuiz;
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    //EditTeamsAdapter editTeamsAdapter;
    //ShowTeamsAdapter showTeamsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_quizmaster_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                thisQuiz.updateTeams(C_EditOrShowTeams.this);
                break;

            case R.id.download:
                QuizLoader quizLoader = new QuizLoader(C_EditOrShowTeams.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadTeams();
                break;

            case R.id.refresh:
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_edit_or_show_teams);

        thisQuiz = MyApplication.theQuiz;
        rvTeams = findViewById(R.id.rvShowTeams);

        rvTeams.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvTeams.setLayoutManager(layoutManager);
        if (thisQuiz.getMyLoginentity().getType().equals(LoginEntity.SELECTION_QUIZMASTER)){
            adapter = new ShowTeamsAdapter(this,thisQuiz.getTeams(),1);
        }
        else {
            adapter = new EditTeamsAdapter(this, thisQuiz.getTeams());
        }
        rvTeams.setAdapter(adapter);


    }
}
