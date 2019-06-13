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

import com.example.paperlessquiz.adapters.EditTeamsAdapter;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Home screen for receptionist. Allows setting team status and change team names.
 * Actions: Refresh/Dorst/Upload
 */


public class C_ReceptionistHome extends AppCompatActivity implements LoadingActivity {


    //TODO: create own action bar = default
    //TODO: Hide RndSpinnner for receptionist + create own actionbar + make field editable when clicked only


    Quiz thisQuiz = MyApplication.theQuiz;
    RecyclerView rvTeams;
    RecyclerView.LayoutManager layoutManager;
    EditTeamsAdapter editTeamsAdapter;

    @Override
    public void loadingComplete() {
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
        setContentView(R.layout.c_act_receptionist_home);
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
        editTeamsAdapter = new EditTeamsAdapter(this, thisQuiz.getTeams());
        rvTeams.setAdapter(editTeamsAdapter);
    }
}
