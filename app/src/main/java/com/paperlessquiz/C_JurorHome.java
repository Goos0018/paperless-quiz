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

/**
 * Home page for the juror. Allows to answer remarks from the teams. Also used by the bar responsible for the same reason.
 */
public class C_JurorHome extends MyActivity implements LoadingActivity {

    RecyclerView rvDisplayHelpTopics;
    RecyclerView.LayoutManager layoutManager;
    int helpType;
    ShowHelpTopicsAdapter showHelpTopicsAdapter;
    ArrayList<HelpTopic> topics = new ArrayList<>();
    User thisUser = thisQuiz.getThisUser();
    QuizLoader quizLoader;
    boolean remarksLoaded, showAllMessages;

    @Override
    public void loadingComplete(int requestId) {
        switch (requestId) {
            case QuizDatabase.REQUEST_ID_GET_REMARKS:
                remarksLoaded = true;
                break;
        }
        if (remarksLoaded) {
            remarksLoaded = false;
            showHelpTopicsAdapter.setHelpTopics(quizLoader.getRemarksRequest.getResultsList());
            showHelpTopicsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_c_juror_home);
        setActionBarIcon();
        setActionBarTitle();
        quizLoader = new QuizLoader(this);
        switch (thisQuiz.getThisUser().getUserType()) {
            case QuizDatabase.USERTYPE_JUROR:
                helpType = QuizDatabase.HELPTYPE_QUIZQUESTION;
                break;
            case QuizDatabase.USERTYPE_BARRESPONSIBLE:
                helpType = QuizDatabase.HELPTYPE_ORDERQUESTION;
                break;
            default:
                helpType = QuizDatabase.HELPTYPE_TEAM; // This should never happen, only bar respsonsible and juror have this screen
        }

        quizLoader.loadRemarks(helpType, showAllMessages);
        rvDisplayHelpTopics = findViewById(R.id.rvDisplayHelpTopics);
        rvDisplayHelpTopics.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvDisplayHelpTopics.setLayoutManager(layoutManager);
        showHelpTopicsAdapter = new ShowHelpTopicsAdapter(this, topics, helpType);
        rvDisplayHelpTopics.setAdapter(showHelpTopicsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.juror, menu);
        //Hide the back button for the Juror
        if (thisQuiz.getThisUser().getUserType() == QuizDatabase.USERTYPE_JUROR) {
            menu.findItem(R.id.back).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                quizLoader.loadRemarks(helpType, showAllMessages);
                break;
            case R.id.allmessages:
                showAllMessages = !showAllMessages;
                quizLoader.loadRemarks(helpType, showAllMessages);
                break;
            case R.id.back:
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

