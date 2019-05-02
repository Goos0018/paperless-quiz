package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paperlessquiz.quiz.Quiz;
import com.example.paperlessquiz.quizgetter.QuizGetter;

//After a quiz was selected, determine if you want to log in as a participant or an organizer
public class SelectRole extends AppCompatActivity {

    String quizSheetID, sheetName,scriptParams;
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
        QuizGetter thisQuizGetter = (QuizGetter) getIntent().getSerializableExtra("ThisQuizGetter");
        btnParticipant.setOnClickListener(new View.OnClickListener() {
                  @Override
            public void onClick(View view){
                      if (thisQuizGetter.isOpen()){
                          tvWelcome.setText("Welcome to" + thisQuizGetter.getName());
                          Intent intent = new Intent(SelectRole.this, com.example.paperlessquiz.LogInToQuizAsParticipant.class);
                          intent.putExtra("ThisQuizGetter",thisQuizGetter);
                          startActivity(intent);
                      }
                      else {
                          tvWelcome.setText("This quiz is not open yet");
                      }

                  }

        });

        btnOrganizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //TODO: modify to ...Organizer
                Intent intent = new Intent(SelectRole.this, com.example.paperlessquiz.LogInToQuizAsParticipant.class);
                intent.putExtra("thisQuizGetter",thisQuizGetter);
                startActivity(intent);
            }

        });




    }
}
