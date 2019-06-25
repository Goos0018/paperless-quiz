package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.paperlessquiz.google.access.LoadingActivity;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizGenerator;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;

/**
 * This screen allows you to select if you are an organizer or a participant.
 * All data about the quiz is loaded and stored in a Quiz object
 */

public class A_SelectRole extends AppCompatActivity implements LoadingActivity {

    Quiz thisQuiz;
    QuizLoader quizLoader;
    Intent intent;
    Button btnParticipant;
    Button btnOrganizer;
    TextView tvQuizName, tvWelcome;
    ImageView ivQuizLogo;
    int counter = 0;

    @Override
    public void loadingComplete() {
        //Do something when loading is complete - not needed here
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_a_select_role);

        btnParticipant = findViewById(R.id.btn_participant);
        btnOrganizer = findViewById(R.id.btn_organizer);
        tvQuizName = findViewById(R.id.tvQuizName);
        tvWelcome = findViewById(R.id.tvWelcome);
        ivQuizLogo = findViewById(R.id.ivQuizLogo);
        thisQuiz = MyApplication.theQuiz;
        tvQuizName.setText(thisQuiz.getListData().getName());
        tvWelcome.setText(thisQuiz.getListData().getDescription());
        String URL = thisQuiz.getListData().getLogoURL();
        if (URL.equals("")) {
            ivQuizLogo.setImageResource(R.mipmap.placeholder);
        } else {
            Picasso.with(this)
                    .load(URL)
                    .resize(Quiz.TARGET_WIDTH, Quiz.TARGET_HEIGHT)
                    .centerCrop()
                    .into(ivQuizLogo);
        }
        quizLoader = new QuizLoader(this, thisQuiz.getListData().getSheetDocID());
        quizLoader.loadAll();
        //Access the Generator screen by tapping the logo 5 times
        ivQuizLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if (counter == 5) {
                    Intent intentG = new Intent(A_SelectRole.this, A__GenerateQuiz.class);
                    startActivity(intentG);
                }
            }
        });
        btnParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonActions(QuizGenerator.SELECTION_PARTICIPANT);
            }
        });

        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonActions(QuizGenerator.SELECTION_ORGANIZER);
            }
        });
    }

    public void commonActions(String selection) {
        //if we are here, all loading actions should be finished, so we can set the result in the central Quiz object and make sure this is properly initialized
        //TODO: put this as a method of the QuizLoader class
        //thisQuiz.setListData(thisQuizListData); //=> Already done in the main activity
        thisQuiz.setTeams(quizLoader.quizTeamsLPL.getLoginEntities());
        //thisQuiz.getAdditionalData().setNrOfParticipants(thisQuiz.getTeams().size());
        thisQuiz.setOrganizers(quizLoader.quizOrganizersLPL.getLoginEntities());
        thisQuiz.setRounds(quizLoader.quizRoundsLPL.getRounds()); //=> not necessary, always done inside the LPL when retrieving rounds info
        //thisQuiz.getAdditionalData().setNrOfRounds(thisQuiz.getRounds().size());
        //thisQuiz.setAllScoresPerTeam();=> not necessary, done in the LPL
        thisQuiz.setAllQuestionsPerRound(quizLoader.quizQuestionsLPL.getAllQuestionsPerRound()); //this will work because the Quiz should have all rounds initialized
        thisQuiz.setAllAnswersPerQuestion(quizLoader.quizAnswersLPL.getAllAnswersPerRound());    //this will work because the Quiz should have all questions for each round initialized
        thisQuiz.setAllCorrectionsPerQuestion(quizLoader.quizCorrectionsLPL.getAllCorrectionsPerRound());    //this will work because the Quiz should have all questions for each round initialized
        thisQuiz.initializeResultsForTeams();
        thisQuiz.loadingCompleted = true; //So now we know we can update rounds, questions, ...
        intent = new Intent(A_SelectRole.this, B_LoginMain.class);
        //intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
        intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, selection);
        startActivity(intent);
    }

}
