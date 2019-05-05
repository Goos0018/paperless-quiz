package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paperlessquiz.google.access.GoogleAccessGet;
import com.example.paperlessquiz.google.access.LoadingListenerImpl;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.quizextras.QuizExtrasParser;
import com.example.paperlessquiz.quizextras.UpdateQuizBasicsObjectLPL;
import com.example.paperlessquiz.quizbasics.QuizBasics;

/* This screen allows you to select if you are an organizer or a participant.
Additional details about the quiz are retrieved from the sheet that is passed in quizBasics
These details are stored in thisQuizExtras.
TODO: string resources and constants
TODO: layout
 */

public class A_SelectRole extends AppCompatActivity {

    String quizSheetID, scriptParams;
    String quizDataSheetName = "QuizData";
    boolean quizIsOpen;
    Button btnParticipant;
    Button btnOrganizer;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);

        btnParticipant=findViewById(R.id.btn_participant);
        btnOrganizer=findViewById(R.id.btn_organizer);
        tvWelcome=findViewById(R.id.tv_welcome);
        QuizBasics thisQuizBasics = (QuizBasics) getIntent().getSerializableExtra("thisQuizBasics");
        QuizExtras quizExtras = new QuizExtras();
        String scriptParams = "DocID=" + thisQuizBasics.getSheetDocID() + "&Sheet=" + quizDataSheetName + "&action=getdata";
        GoogleAccessGet<QuizExtras> googleAccessGet = new GoogleAccessGet<QuizExtras>(this, scriptParams);
        final UpdateQuizBasicsObjectLPL updateQuizBasicsObjectLPL = new UpdateQuizBasicsObjectLPL(quizExtras);
        googleAccessGet.getItems(new QuizExtrasParser(), updateQuizBasicsObjectLPL,
                new LoadingListenerImpl(this, "Please wait", "Loading quiz", "Something went wrong: "));

        btnParticipant.setOnClickListener(new View.OnClickListener() {
                  @Override
            public void onClick(View view){
                      if (updateQuizBasicsObjectLPL.getQuizExtras().isOpen()){
                          tvWelcome.setText("Welcome to" + thisQuizBasics.getName() + " QuizDetails open is " + quizExtras.isOpen());
                          Intent intent = new Intent(A_SelectRole.this, SelectLoginName.class);
                          intent.putExtra("thisQuizBasics", thisQuizBasics);
                          intent.putExtra("thisQuizExtras", quizExtras);
                          intent.putExtra("thisLoginType", "Participant");
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
                Intent intent = new Intent(A_SelectRole.this, SelectLoginName.class);
                intent.putExtra("thisQuizBasics", thisQuizBasics);
                intent.putExtra("thisQuizExtras", quizExtras);
                intent.putExtra("thisLoginType", "Organizer");
                startActivity(intent);
            }

        });




    }
}
