package com.example.paperlessquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.paperlessquiz.adapters.ShowTeamsAdapter;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Quizmaster screen to show the teams and whether or not they submitted answers for a particular round
 * Actions: Refresh/Dorst/Rounds/Upload
 */
public class C_QuizmasterTeams extends AppCompatActivity implements Frag_RndSpinner.HasRoundSpinner, LoadingActivity {
    //TODO: Move code toset icon and nameto separate class/location
    Quiz thisQuiz = MyApplication.theQuiz;
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    ShowTeamsAdapter showTeamsAdapter;
    int roundNr;

    @Override
    public void onRoundChanged(int roundNr) {
        this.roundNr = roundNr;
        if (thisQuiz.getMyLoginentity().getType().equals(LoginEntity.SELECTION_QUIZMASTER) && showTeamsAdapter != null) {
            showTeamsAdapter.setRoundNr(roundNr);
            showTeamsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadingComplete() {
        //Do something when loading is complete
        if (showTeamsAdapter != null) {
            showTeamsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_quizmaster_home, menu);
        //Hide item for the Teams button
        MenuItem item = menu.findItem(R.id.teams);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                thisQuiz.updateTeams(C_QuizmasterTeams.this);
                break;

            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_QuizmasterTeams.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadTeams();
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
        setContentView(R.layout.c_act_quizmaster_teams);
        //Set the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Display the "back" icon, we will replace this with the icon of this Quiz
        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                //Log.d("DEBUG", "onBitmapLoaded");
                BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                //                                mBitmapDrawable.setBounds(0,0,24,24);
                // setting icon of Menu Item or Navigation View's Menu Item
                //actionBar.setIcon(mBitmapDrawable);
                actionBar.setHomeAsUpIndicator(mBitmapDrawable);
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {
                //Log.d("DEBUG", "onBitmapFailed");
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
                //Log.d("DEBUG", "onPrepareLoad");
            }
        };
        String URL = thisQuiz.getListData().getLogoURL();
        if (URL.equals("")) {
            actionBar.setDisplayHomeAsUpEnabled(false);//If the Quiz has no logo, then don't display anything
        } else {
            //Picasso.with(this).load("http://www.meerdaal.be//assets/logo-05c267018885eb67356ce0b49bf72129.png").into(mTarget);
            Picasso.with(this).load(thisQuiz.getListData().getLogoURL()).resize(Quiz.ACTIONBAR_ICON_WIDTH, Quiz.ACTIONBAR_ICON_HEIGHT).into(mTarget);
        }
        actionBar.setTitle(thisQuiz.getMyLoginentity().getName());
        rvTeams = findViewById(R.id.rvShowTeams);
        rvTeams.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvTeams.setLayoutManager(layoutManager);
        showTeamsAdapter = new ShowTeamsAdapter(this, thisQuiz.getTeams(), roundNr);
        rvTeams.setAdapter(showTeamsAdapter);
    }
}
