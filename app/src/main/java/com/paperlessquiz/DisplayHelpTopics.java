package com.paperlessquiz;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.paperlessquiz.adapters.ShowHelpTopicsAdapter;
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.HelpTopic;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.users.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DisplayHelpTopics extends AppCompatActivity implements LoadingActivity {

    RecyclerView rvDisplayHelpTopics;
    RecyclerView.LayoutManager layoutManager;
    int helpType;
    ShowHelpTopicsAdapter showHelpTopicsAdapter;
    ArrayList<HelpTopic> topics = new ArrayList<>();
    Quiz thisQuiz = MyApplication.theQuiz;
    User thisUser = thisQuiz.getThisUser();
    QuizLoader quizLoader;
    boolean helpTopicsLoaded;

    @Override
    public void loadingComplete(int requestId) {
        switch (requestId) {
            case QuizDatabase.REQUEST_ID_GET_HELPTOPICS:
                helpTopicsLoaded = true;
                break;
        }
        if (helpTopicsLoaded) {
            helpTopicsLoaded = false;
            showHelpTopicsAdapter.setHelpTopics(quizLoader.getHelpTopicsRequest.getResultsList());
            showHelpTopicsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_display_help_topics);
        ActionBar actionBar = getSupportActionBar();
        String actionBarTitle;
        quizLoader = new QuizLoader(this);
        helpType = getIntent().getIntExtra(QuizDatabase.INTENT_EXTRANAME_HELPTYPE, 0);
        quizLoader.loadHelpTopics(helpType);
        switch (helpType) {
            case (QuizDatabase.HELPTYPE_QUIZQUESTION):
            case (QuizDatabase.HELPTYPE_ORDERQUESTION):
                switch (thisUser.getUserType()) {
                    case QuizDatabase.USERTYPE_BARRESPONSIBLE:
                    case QuizDatabase.USERTYPE_JUROR:
                        actionBarTitle = "Opmerkingen";
                        break;
                    default:
                        actionBarTitle = "Mijn opmerkingen";
                }
                break;
            default:
                actionBarTitle = "Help";
        }
        actionBar.setTitle(actionBarTitle);

        rvDisplayHelpTopics = findViewById(R.id.rvDisplayHelpTopics);
        rvDisplayHelpTopics.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvDisplayHelpTopics.setLayoutManager(layoutManager);

        showHelpTopicsAdapter = new ShowHelpTopicsAdapter(this, topics);
        rvDisplayHelpTopics.setAdapter(showHelpTopicsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.helptopics, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                quizLoader.loadHelpTopics(helpType);
                break;
            case R.id.backtoquiz:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //Used to create the time parameter that is passed when creating orders
    public String getCurrentTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        dateFormat.setTimeZone(TimeZone.getDefault());
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }
}

