package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.quizextras.QuizExtrasParser;
import com.example.paperlessquiz.quizextras.GetQuizExtrasLPL;
import com.example.paperlessquiz.quizbasics.QuizBasics;

/* This screen allows you to select if you are an organizer or a participant.
Additional details about the quiz are retrieved from the sheet that is passed in quizBasics
These details are stored in thisQuizExtras.
TODO: string resources and constants
TODO: layout
 */

public class A_SelectRole extends AppCompatActivity {

    String quizSheetID, scriptParams;
    boolean quizIsOpen;
    Button btnParticipant;
    Button btnOrganizer;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_select_role);

        btnParticipant=findViewById(R.id.btn_participant);
        btnOrganizer=findViewById(R.id.btn_organizer);
        tvWelcome=findViewById(R.id.tv_welcome);
        QuizBasics thisQuizBasics = (QuizBasics) getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        QuizExtras quizExtras = new QuizExtras();
        String scriptParams = GoogleAccess.PARAMNAME_DOC_ID + thisQuizBasics.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizExtrasParser.QUIZ_EXTRAS_SHEETNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<QuizExtras> googleAccessGet = new GoogleAccessGet<QuizExtras>(this, scriptParams);
        final GetQuizExtrasLPL getQuizExtrasLPL = new GetQuizExtrasLPL(quizExtras);
        googleAccessGet.getItems(new QuizExtrasParser(), getQuizExtrasLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz", "Something went wrong: "));

        btnParticipant.setOnClickListener(new View.OnClickListener() {
                  @Override
            public void onClick(View view){
                      if (getQuizExtrasLPL.getQuizExtras().isOpen()){
                          tvWelcome.setText("Welcome to" + thisQuizBasics.getName() + " QuizDetails open is " + quizExtras.isOpen());
                          Intent intent = new Intent(A_SelectRole.this, B_SelectLoginName.class);
                          intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                          intent.putExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, quizExtras);
                          intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, LoginEntity.SELECTION_PARTICIPANT);
                          startActivity(intent);
                      }
                      else {
                          tvWelcome.setText("QuizDetails " + thisQuizBasics.getName() + " is not open yet: " + quizExtras.isOpen());
                      }

                  }

        });

        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(A_SelectRole.this, B_SelectLoginName.class);
                intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                intent.putExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, quizExtras);
                intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, LoginEntity.SELECTION_ORGANIZER);
                startActivity(intent);
            }

        });




    }
}
