package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.answer.Answer;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessSet;
import com.example.paperlessquiz.google.access.LoadingListenerSilent;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.Date;

public class B_LoginMain extends AppCompatActivity implements B_frag_ListEntities.ItemSelected {
    //Extra objects from intent
    Quiz thisQuiz;
    LoginEntity thisLoginEntity;
    //Local items in interface
    TextView tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    String loginType;
    //other local variables needed
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_act_login_main);

        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvDisplayID = findViewById(R.id.tvDisplayID);
        btnSubmit = (Button) findViewById(R.id.btn_submit_login);
        etPasskey = (EditText) findViewById(R.id.et_passkey);
        thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        loginType = (String) getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        onItemSelected(0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Integer.valueOf(tvDisplayID.getText().toString());
                String input = etPasskey.getText().toString().trim();
                if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
                    thisLoginEntity = thisQuiz.getTeam(id - 1);
                    //Get the answers for this participant
                    //For each round
                    for (int i = 0; i < thisQuiz.getAllAnswers().size(); i++) {
                        //For each question
                        for (int j = 0; j < thisQuiz.getAllAnswers().get(i).size(); j++) {
                            Answer tmp = thisQuiz.getAllAnswers().get(i).get(j).getAllAnswers().get(thisLoginEntity.getId()-1);
                            thisQuiz.getMyAnswers().get(i).set(j,tmp );
                        }
                    }
                } else {
                    thisLoginEntity = thisQuiz.getOrganizer(id - 1);
                }
                if (input.isEmpty()) {
                    Toast.makeText(B_LoginMain.this, "Please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                } else {
                    if (input.equals(thisLoginEntity.getPasskey())) {
                        if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_PARTICIPANT)) {
                            Intent intent = new Intent(B_LoginMain.this, C_ParticipantHome.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(B_LoginMain.this, C_ParticipantHome.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(B_LoginMain.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(int index) {
        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
            tvDisplayName.setText(thisQuiz.getTeams().get(index).getName());
            tvDisplayID.setText(Integer.toString(thisQuiz.getTeams().get(index).getId()));
        } else {
            tvDisplayName.setText(thisQuiz.getOrganizer(index).getName());
            tvDisplayID.setText(Integer.toString(thisQuiz.getOrganizer(index).getId()));
        }
    }
}
