package com.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.squareup.picasso.Picasso;

/**
 * After having selected a Quiz, this screen allows you to select if you are an organizer or a participant.
 * We also load the list of Quiz users (teams and organizers) into the central Quiz object, so they can be selected in the next screen.
 * This userlist can also be retrieved anonymously.
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
    public void loadingComplete(int requestID) {
        //Write all the data we retrieved to the Quiz object
        switch (requestID){
            case QuizDatabase.REQUEST_ID_GET_USERS:
                //Load the users that were retrieved into the Organizers and the Teams
                quizLoader.loadUsersIntoQuiz();
                break;
            case QuizDatabase.REQUEST_ID_GET_ROUNDS:
                break;
            case QuizDatabase.REQUEST_ID_GET_QUESTIONS:
                break;

            case QuizDatabase.REQUEST_ID_GET_ANSWERS:
                break;
            case QuizDatabase.REQUEST_ID_GET_ANSWERSSUBMITTED:
                break;
            case QuizDatabase.REQUEST_ID_GET_EVENTLOGS:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_a_select_role);
        //Get the stuff from the interface
        btnParticipant = findViewById(R.id.btn_participant);
        btnOrganizer = findViewById(R.id.btn_organizer);
        tvQuizName = findViewById(R.id.tvQuizName);
        tvWelcome = findViewById(R.id.tvWelcome);
        ivQuizLogo = findViewById(R.id.ivQuizLogo);
        thisQuiz = MyApplication.theQuiz;
        tvQuizName.setText(thisQuiz.getListData().getName());
        tvWelcome.setText(thisQuiz.getListData().getDescription());
        //Get the quiz logo
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
        //Load the users from the Quiz
        quizLoader = new QuizLoader(this);
        quizLoader.loadUsers();
        //Access the Generator screen by tapping the logo 5 times
        ivQuizLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                if (counter == 5) {
                    counter=0;
                    //Intent intentG = new Intent(A_SelectRole.this, A__GenerateQuiz.class);
                    //startActivity(intentG);
                }
            }
        });
        //Login as participant
        btnParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // commonActions(QuizGenerator.SELECTION_PARTICIPANT);
                intent = new Intent(A_SelectRole.this, B_Login.class);
                intent.putExtra(QuizDatabase.INTENT_EXTRANAME_IS_ORGANIZER,false);
                startActivity(intent);
            }
        });
        //Login as organizer
        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //commonActions(QuizGenerator.SELECTION_ORGANIZER);
                intent = new Intent(A_SelectRole.this, B_Login.class);
                intent.putExtra(QuizDatabase.INTENT_EXTRANAME_IS_ORGANIZER,true);
                startActivity(intent);
            }
        });
    }

    public void commonActions(String selection) {
        //if we are here, all loading actions should be finished, so we can set the result in the central Quiz object and make sure this is properly initialized
        //TODO: put this as a method of the QuizLoader class
        //thisQuiz.setListData(thisQuizListData);                                                           //=> Already done in the main activity - is done only once
        //thisQuiz.setTeams(quizLoader.quizTeamsLPL.getLoginEntities());                                      //=> Set the teams here, LPL will update them on subsequent loads
        // TODO: thisQuiz.setOrganizers(quizLoader.quizOrganizersLPL.getLoginEntities());                            //=> Not in LPL, only done once
        //thisQuiz.setRounds(quizLoader.quizRoundsLPL.getRounds());                                           //=> Set the rounds here, LPL will update them on subsequent loads
        //thisQuiz.setAllQuestionsPerRound(quizLoader.quizQuestionsLPL.getAllQuestionsPerRound());            //=> Rounds exist now. Questions are only loaded here.
        //thisQuiz.setAllAnswersPerQuestion(quizLoader.quizAnswersLPL.getAllAnswersPerRound());               //=> Questions exist now. LPL will update the answer strings on subsequent loads.
        //thisQuiz.setAllCorrectionsPerQuestion(quizLoader.quizCorrectionsLPL.getAllCorrectionsPerRound());   //=> Questions exist now. LPL will update the answer booleans on subsequent loads.
        //thisQuiz.initializeResultsForTeams();                                                               //=> Initialize this simply. Calculation done based on corrections.
        //thisQuiz.loadingCompleted = true;                                                                   //=> Log this so the LPL's know it
        MyApplication.setDebugLevel(thisQuiz.getListData().getDebugLevel());
        //MyApplication.setKeepLogs(thisQuiz.getListData().isKeepLogs());
        //MyApplication.setAppDebugLevel(thisQuiz.getListData().getAppDebugLevel());

    }

}
