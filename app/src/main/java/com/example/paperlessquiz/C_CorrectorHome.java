package com.example.paperlessquiz;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paperlessquiz.adapters.CorrectAnswersAdapter;
import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.round.Round;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

/**
 * Home screen for the corrector. Allows to correct questions
 * - per team/round (all answers for one team for one round)
 * - or per question (all answers of all teams)
 * Activity uses roundSpinner and questionSpinner fragments to scroll through round and questions
 * Actions: Refresh/Dorst/All wrong/All correct/Per question/per team
 */

public class C_CorrectorHome extends AppCompatActivity implements LoadingActivity, Frag_RndSpinner.HasRoundSpinner, Frag_QuestionSpinner.QuestionHasChanged {
    Quiz thisQuiz = MyApplication.theQuiz;
    int thisTeamNr, thisRoundNr, thisQuestionNr = 1;
    TextView tvCorrectAnswer;
    Button btnSubmitCorrections;
    LinearLayout llCorrectQuestions;
    ListView lvCorrectQuestions;
    CorrectAnswersAdapter myAdapter;
    Frag_QuestionSpinner qSpinner;
    ArrayList<Answer> allAnswers;

    @Override
    public void loadingComplete() {
        //Actions do do when we are completed (re)loading data from the Google sheet
        refresh();
    }

    @Override
    public void onRoundChanged(int roundNr) {
        this.thisRoundNr = roundNr;
        qSpinner.setRoundNr(roundNr);
        refresh();
    }

    @Override
    public void onQuestionChanged(int oldQuestionNr, int newQuestionNr) {
        this.thisQuestionNr = newQuestionNr;
        refresh();
    }

    private void refresh() {
        //Depending on the round status, show the llCorrectQuestions and add the correct answers to the adapter
        Round thisRound = thisQuiz.getRound(thisRoundNr);
        if (thisRound.getAcceptsCorrections()) {
            llCorrectQuestions.setVisibility((View.VISIBLE));
        } else {
            llCorrectQuestions.setVisibility((View.GONE));
        }

        allAnswers = thisQuiz.getQuestion(thisRoundNr, thisQuestionNr).getAllAnswers();
        myAdapter = new CorrectAnswersAdapter(this, allAnswers);
        lvCorrectQuestions.setAdapter(myAdapter);
        tvCorrectAnswer.setText(thisQuiz.getQuestion(thisRoundNr, thisQuestionNr).getCorrectAnswer());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.corrector, menu);
        //Hide item for the correction per question button
        MenuItem item = menu.findItem(R.id.rounds);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                QuizLoader quizLoader = new QuizLoader(C_CorrectorHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();
                refresh();
                break;
            case R.id.rounds:
                break;
            case R.id.allcorrect:
                for (int i = 0; i < allAnswers.size(); i++) {
                    allAnswers.get(i).setCorrect(true);
                    myAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.allwrong:
                for (int i = 0; i < allAnswers.size(); i++) {
                    allAnswers.get(i).setCorrect(false);
                    myAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_corrector_home);
        //Get the questionSpinner fragment
        qSpinner = (Frag_QuestionSpinner) getSupportFragmentManager().findFragmentById(R.id.frQuestionSpinner);
        //Set the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Display the "back" icon, we will replace this with the icon of this Quiz
        final Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                actionBar.setHomeAsUpIndicator(mBitmapDrawable);
            }

            @Override
            public void onBitmapFailed(Drawable drawable) {
            }

            @Override
            public void onPrepareLoad(Drawable drawable) {
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
        //Log that the user logged in
        thisTeamNr = thisQuiz.getMyLoginentity().getId();
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "Logged in");
        //Get all the stuff from the layout
        llCorrectQuestions = findViewById(R.id.llCorrectQuestions);
        tvCorrectAnswer = findViewById(R.id.tvCorrectAnswer);
        btnSubmitCorrections = findViewById(R.id.btnSubmitCorrections);
        lvCorrectQuestions = findViewById(R.id.lvCorrectQuestions);
        onRoundChanged(1);
        btnSubmitCorrections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thisQuiz.submitCorrectionsForQuestion(C_CorrectorHome.this, thisRoundNr, thisQuestionNr);
            }
        });
    }

    @Override
    protected void onPause() {
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "WARNING: Paused the app");
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MyApplication.eventLogger.logEvent(thisQuiz.getMyLoginentity().getName(), "WARNING: Resumed the app");
    }
}
