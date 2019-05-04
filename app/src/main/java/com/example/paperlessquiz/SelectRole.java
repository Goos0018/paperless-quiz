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

//After a quiz was selected, determine if you want to log in as a participant or an organizer
public class SelectRole extends AppCompatActivity {

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
        QuizBasics thisQuizBasics = (QuizBasics) getIntent().getSerializableExtra("thisQuizDetails");
        QuizExtras quizExtras = new QuizExtras();
        String scriptParams = "DocID=" + thisQuizBasics.getSheetDocID() + "&Sheet=" + quizDataSheetName + "&action=getdata";
        GoogleAccessGet<QuizExtras> googleAccessGet = new GoogleAccessGet<QuizExtras>(this, scriptParams);
        googleAccessGet.getItems(new QuizExtrasParser(), new UpdateQuizBasicsObjectLPL(quizExtras),
                new LoadingListenerImpl(this, "Please wait", "Loading quiz", "Something went wrong: "));

        btnParticipant.setOnClickListener(new View.OnClickListener() {
                  @Override
            public void onClick(View view){
                      if (quizExtras.isOpen()){
                          tvWelcome.setText("Welcome to" + thisQuizBasics.getName() + " QuizDetails open is " + quizExtras.isOpen());
                          Intent intent = new Intent(SelectRole.this, com.example.paperlessquiz.LogInToQuizAsParticipant.class);
                          intent.putExtra("thisQuizExtras", quizExtras);
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
                //TODO: modify to ...Organizer
                Intent intent = new Intent(SelectRole.this, com.example.paperlessquiz.LogInToQuizAsParticipant.class);
                intent.putExtra("thisQuizBasics", thisQuizBasics);
                startActivity(intent);
            }

        });




    }
}
