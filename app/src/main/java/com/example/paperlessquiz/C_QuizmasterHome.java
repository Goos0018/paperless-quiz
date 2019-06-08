package com.example.paperlessquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.paperlessquiz.adapters.RoundsAdapter;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class C_QuizmasterHome extends AppCompatActivity {

    Quiz thisQuiz;
    ListView lvRounds, lvTeams;
    //RecyclerView.LayoutManager layoutManager;
    RoundsAdapter showRoundsAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.c_quizmaster_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upload:
                thisQuiz.updateRounds(C_QuizmasterHome.this);
                break;

            case R.id.download:
                QuizLoader quizLoader = new QuizLoader(C_QuizmasterHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();
                break;

            case R.id.refresh:
                showRoundsAdapter.notifyDataSetChanged();
                break;

            case R.id.teams:
                Intent intent = new Intent(C_QuizmasterHome.this, C_EditOrShowTeams.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_quizmaster_home);
        thisQuiz = MyApplication.theQuiz;
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

        lvRounds = findViewById(R.id.lvRounds);

        //lvRounds.setHasFixedSize(true);
        //layoutManager = new LinearLayoutManager(this);
        //lvRounds.setLayoutManager(layoutManager);
        showRoundsAdapter = new RoundsAdapter(this, thisQuiz.getRounds());
        lvRounds.setAdapter(showRoundsAdapter);


    }
}
