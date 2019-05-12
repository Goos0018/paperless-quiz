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
import com.example.paperlessquiz.loginentity.AddLoginEntitiesToParticipantsAdapterLPL;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.loginentity.LoginEntityParser;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.quizextras.QuizExtrasParser;
import com.example.paperlessquiz.quizextras.GetQuizExtrasLPL;
import com.example.paperlessquiz.quizbasics.QuizBasics;

/* This screen allows you to select if you are an organizer or a participant.
All data about the quiz is loaded and stored in a Quiz object
TODO: string resources and constants
TODO: layout
 */

public class A_SelectRole extends AppCompatActivity {

    Quiz thisQuiz = new Quiz();
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
        //Get the additional data we don't have yet: nr of rounds, nr of participants, status,  ...
        //QuizExtras quizExtras = new QuizExtras();
        String scriptParamsForExtraData = GoogleAccess.PARAMNAME_DOC_ID + thisQuizBasics.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + QuizExtrasParser.QUIZ_EXTRAS_SHEETNAME + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<QuizExtras> googleAccessGetQuizExtraData = new GoogleAccessGet<QuizExtras>(this, scriptParamsForExtraData);
        final GetQuizExtrasLPL getQuizExtrasLPL = new GetQuizExtrasLPL();
        googleAccessGetQuizExtraData.getItems(new QuizExtrasParser(), getQuizExtrasLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz data", "Something went wrong: "));
        //Get the list of participating teams
        String scriptParamsForTeams= GoogleAccess.PARAMNAME_DOC_ID + thisQuizBasics.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_SHEET + "Teams" + GoogleAccess.PARAM_CONCATENATOR +
                GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_GETDATA;
        GoogleAccessGet<LoginEntity> googleAccessGetTeams = new GoogleAccessGet<LoginEntity>(this, scriptParamsForTeams);
        final AddLoginEntitiesToParticipantsAdapterLPL teamsLPL = new AddLoginEntitiesToParticipantsAdapterLPL();
        googleAccessGetTeams.getItems(new LoginEntityParser(), teamsLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz participants...", "Something went wrong: "));


        btnParticipant.setOnClickListener(new View.OnClickListener() {
                  @Override
            public void onClick(View view){
                      if (getQuizExtrasLPL.getQuizExtras().isOpen()){
                          //if we are here, all ooading actions should be finished, so we can set the result in the Quiz object
                          //tvWelcome.setText("Welcome to" + thisQuizBasics.getName() + " QuizDetails open is " + quizExtras.isOpen());
                          thisQuiz.setListData(thisQuizBasics);
                          thisQuiz.setAdditionalData(getQuizExtrasLPL.getQuizExtras());
                          thisQuiz.setTeams(teamsLPL.getLoginEntities());
                          Intent intent = new Intent(A_SelectRole.this, B_SelectLoginName.class);
                          intent.putExtra("ThisQuiz", thisQuiz);
                          //intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                          //intent.putExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, quizExtras);
                          intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, LoginEntity.SELECTION_PARTICIPANT);
                          startActivity(intent);
                      }
                      else {
                          tvWelcome.setText("QuizDetails " + thisQuizBasics.getName() + " is not open yet");
                      }

                  }

        });

        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Intent intent = new Intent(A_SelectRole.this, B_SelectLoginName.class);
                //intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                //intent.putExtra(QuizExtras.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, quizExtras);
                //intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE, LoginEntity.SELECTION_ORGANIZER);
                //startActivity(intent);
            }

        });




    }
}
