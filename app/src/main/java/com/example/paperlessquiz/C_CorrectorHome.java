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

public class C_CorrectorHome extends AppCompatActivity implements LoadingActivity, FragSecondarySpinner.HasSecondarySpinner, Frag_RndSpinner.HasRoundSpinner {

    Quiz thisQuiz = MyApplication.theQuiz;
    int thisTeamNr, thisRoundNr, thisSecSpinnerPos = 1;
    TextView tvCorrectAnswer;
    Button btnSubmitCorrections;
    LinearLayout llCorrectQuestions;
    ListView lvCorrectQuestions;
    CorrectAnswersAdapter myAdapter;
    Frag_RndSpinner rndSpinner;
    FragSecondarySpinner qSpinner;
    ArrayList<Answer> allAnswers;
    boolean correctPerQuestion = true;
    QuizLoader quizLoader;

    @Override
    public void onSecondarySpinnerChanged(int oldSecSpinnerNr, int newSecSpinnerNr) {
        this.thisSecSpinnerPos = newSecSpinnerNr;
        refresh();
    }

    @Override
    public String getValueToSetForPrimaryField(int priSpinnerPos, int secSpinnerPos) {
        if (correctPerQuestion) {
            return thisQuiz.getQuestion(priSpinnerPos, secSpinnerPos).getName();
        } else {
            return Integer.toString(thisQuiz.getTeam(secSpinnerPos).getId());
        }
    }

    @Override
    public String getValueToSetForSecondaryField(int priSpinnerPos, int secSpinnerPos) {
        if (correctPerQuestion) {
            return thisQuiz.getQuestion(priSpinnerPos, secSpinnerPos).getDescription();
        } else {
            return thisQuiz.getTeam(secSpinnerPos).getName();
        }
    }

    @Override
    public int getSizeOfSecSpinnerArray(int priSpinnerPos) {
        if (correctPerQuestion) {
            return thisQuiz.getRound(priSpinnerPos).getQuestions().size();
        } else {
            return thisQuiz.getTeams().size();
        }
    }

    @Override
    public void onRoundChanged(int roundNr) {
        this.thisRoundNr = roundNr;
        qSpinner.setPrimarySpinnerPos(roundNr);
        refresh();
    }

    @Override
    public void loadingComplete() {
        //If this was the quizloader for the rounds, we don't want to set the corrections
        if(quizLoader.quizCorrectionsLPL.getAllCorrectionsPerRound().size()==0){} else {
        thisQuiz.setAllCorrectionsPerQuestion(quizLoader.quizCorrectionsLPL.getAllCorrectionsPerRound());}
        rndSpinner.positionChanged();
        qSpinner.refreshDisplay();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.corrector, menu);
        //Hide item for the correction per team/per question button as needed button
        /*
        MenuItem correctPerRndItem = menu.findItem(R.id.rounds);
        MenuItem correctPerTeamItem = menu.findItem(R.id.teams);
        if (correctPerQuestion){correctPerRndItem.setVisible(false);}
        else {correctPerTeamItem.setVisible(false);}
        */
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                quizLoader = new QuizLoader(C_CorrectorHome.this, thisQuiz.getListData().getSheetDocID());
                quizLoader.loadRounds();
                quizLoader.loadAllCorrections();
                break;
            case R.id.teams:
                correctPerQuestion = !correctPerQuestion;
                qSpinner.moveTo(1);
                refresh();
                qSpinner.refreshDisplay();
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

    private void refresh() {
        //Depending on the round status, show the llCorrectQuestions and add the correct answers to the adapter
        Round thisRound = thisQuiz.getRound(thisRoundNr);
        if (thisRound.getAcceptsCorrections()) {
            llCorrectQuestions.setVisibility((View.VISIBLE));
        } else {
            llCorrectQuestions.setVisibility((View.GONE));
        }
        if (correctPerQuestion) {
            allAnswers = thisQuiz.getQuestion(thisRoundNr, thisSecSpinnerPos).getAllAnswers();
            tvCorrectAnswer.setText(thisQuiz.getQuestion(thisRoundNr, thisSecSpinnerPos).getCorrectAnswer());
            tvCorrectAnswer.setVisibility(View.VISIBLE);
        } else {
            allAnswers = thisQuiz.getAnswersForRound(thisRoundNr, thisSecSpinnerPos);
            tvCorrectAnswer.setVisibility(View.GONE);
        }
        myAdapter = new CorrectAnswersAdapter(this, allAnswers);
        lvCorrectQuestions.setAdapter(myAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_act_corrector_home);
        //Get the questionSpinner fragment
        qSpinner = (FragSecondarySpinner) getSupportFragmentManager().findFragmentById(R.id.frQuestionSpinner);
        rndSpinner = (Frag_RndSpinner) getSupportFragmentManager().findFragmentById(R.id.frPriSpinner);
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
                if (correctPerQuestion) {
                    thisQuiz.submitCorrectionsForQuestion(C_CorrectorHome.this, thisRoundNr, thisSecSpinnerPos);
                }
                else
                {
                    thisQuiz.submitCorrectionsForTeam(C_CorrectorHome.this,thisRoundNr,thisSecSpinnerPos);
                }
            }
        });
    }
}
