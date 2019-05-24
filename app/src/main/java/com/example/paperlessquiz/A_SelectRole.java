package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quiz.QuizLoader;
import com.example.paperlessquiz.quizlistdata.QuizListData;

/* This screen allows you to select if you are an organizer or a participant.
All data about the quiz is loaded and stored in a Quiz object
TODO: string resources and constants
TODO: layout
 */

public class A_SelectRole extends AppCompatActivity {

    Quiz thisQuiz = new Quiz();
    QuizLoader quizLoader;
    QuizListData thisQuizListData;
    Intent intent;
    String quizSheetID, scriptParams;
    boolean quizIsOpen;
    Button btnParticipant;
    Button btnOrganizer;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_act_select_role);

        btnParticipant = findViewById(R.id.btn_participant);
        btnOrganizer = findViewById(R.id.btn_organizer);
        tvWelcome = findViewById(R.id.tv_welcome);
        thisQuizListData = (QuizListData) getIntent().getSerializableExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        tvWelcome.setText("Welcome to the " + thisQuizListData.getName() + "\n" + thisQuizListData.getDescription());
        thisQuiz.setListData(thisQuizListData);
        quizLoader = new QuizLoader(this, thisQuiz.getListData().getSheetDocID(), thisQuiz);
        quizLoader.loadAll();

        btnParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quizLoader.quizExtraDataLPL.getQuizExtraData().isOpen()) {
                    commonActions(LoginEntity.SELECTION_PARTICIPANT);
                } else {
                    Toast.makeText(A_SelectRole.this, "This quiz has not been opened yet. " +
                            "Please wait for the quizmaster to open it", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commonActions(LoginEntity.SELECTION_ORGANIZER);
            }

        });
    }

    public void commonActions(String selection) {
        //if we are here, all loading actions should be finished, so we can set the result in the Quiz object
        //First check that all results are OK
        if (!(quizLoader.allChecksOK())) {
            Toast.makeText(A_SelectRole.this, "Attention organizers, " +
                    "something is wrong with your quiz", Toast.LENGTH_LONG).show();
        }
        thisQuiz.setListData(thisQuizListData);
        thisQuiz.setAdditionalData(quizLoader.quizExtraDataLPL.getQuizExtraData());
        thisQuiz.setTeams(quizLoader.quizTeamsLPL.getLoginEntities());
        thisQuiz.setOrganizers(quizLoader.quizOrganizersLPL.getLoginEntities());
        thisQuiz.setRounds(quizLoader.quizRoundsLPL.getRounds());
        thisQuiz.setAllQuestionsPerRound(quizLoader.quizQuestionsLPL.getAllQuestionsPerRound());
        quizLoader.generateBlankAnswers();
        thisQuiz.setMyAnswers(quizLoader.myAnswers);
        intent = new Intent(A_SelectRole.this, B_LoginMain.class);
        intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
        intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, selection);
        startActivity(intent);
    }

}
