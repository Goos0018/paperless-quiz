package com.example.paperlessquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.paperlessquiz.adapters.EditTeamsAdapter;
import com.example.paperlessquiz.adapters.ShowTeamsAdapter;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingListenerNotify;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.loginentity.LoginEntityParser;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;
import com.example.paperlessquiz.round.RoundParser;

import java.util.ArrayList;

public class C_EditTeams extends AppCompatActivity {

    //TODO: make name field and presence editable for Receptionist + toggle presence via icon + hide round AnswersSubmitted image
    //TODO: find a way to hide actions not needed by certain roles
    //TODO: Allow Quizmaster access to this screen but no edit + round taken from previous screen + navigate back


    Quiz thisQuiz;
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    EditTeamsAdapter editTeamsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_quizmaster_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                thisQuiz.updateTeams(C_EditTeams.this);
                /*
                ArrayList<LoginEntity> teamsList = thisQuiz.getTeams();
                String tmp = "[";
                for (int i = 0; i < teamsList.size(); i++) {
                    tmp = tmp + thisQuiz.getTeams().get(i).toString();
                    if (i<teamsList.size()-1){
                        tmp = tmp + ",";
                    }
                    else{
                        tmp = tmp + "]";
                    }
                }

                String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_TEAMS + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_RECORDID + "1" + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_FIELDNAME + LoginEntityParser.NAME + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_NEWVALUES + tmp + GoogleAccess.PARAM_CONCATENATOR +
                        GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_SETDATA;
                GoogleAccessSet googleAccessSet = new GoogleAccessSet(C_EditTeams.this, scriptParams);
                googleAccessSet.setData(new LoadingListenerNotify(C_EditTeams.this, thisQuiz.getMyLoginentity().getName(),
                        "Submitting team updates"));
                        */
                break;

            case R.id.download:
                QuizLoader quizLoader = new QuizLoader(C_EditTeams.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadTeams();
                break;

            case R.id.refresh:
                editTeamsAdapter                                                                                                                                                                                                                                                                                                                                          .notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_edit_teams);

        thisQuiz = MyApplication.theQuiz;
        rvTeams = findViewById(R.id.rvShowTeams);

        rvTeams.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvTeams.setLayoutManager(layoutManager);
        editTeamsAdapter = new EditTeamsAdapter(this, thisQuiz.getTeams());
        rvTeams.setAdapter(editTeamsAdapter);


    }
}
