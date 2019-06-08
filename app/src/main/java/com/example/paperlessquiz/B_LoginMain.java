package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

public class B_LoginMain extends AppCompatActivity implements B_frag_ListEntities.ItemSelected {
    //public class B_LoginMain extends AppCompatActivity implements B_frag_ListEntities.ItemSelected, B_frag_LoginEntity.FragmentListener{
    //Extra objects from intent
    Quiz thisQuiz;
    //String loginType;
    //B_frag_LoginEntity b_frag_loginEntity;

    LoginEntity thisLoginEntity;
    //Local items in interface
    TextView tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    String loginType;
    //other local variables needed
    int teamNr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_act_login_main);
     //   thisQuiz=MyApplication.theQuiz;
     //   loginType = (String) getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
     //  b_frag_loginEntity = getSupportFragmentManager().findFragmentById(R.id.));


        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvDisplayID = findViewById(R.id.tvDisplayID);
        btnSubmit = (Button) findViewById(R.id.btn_submit_login);
        etPasskey = (EditText) findViewById(R.id.et_passkey);
        //thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        thisQuiz=MyApplication.theQuiz;
        loginType = (String) getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        onItemSelected(0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamNr = Integer.valueOf(tvDisplayID.getText().toString());
                String input = etPasskey.getText().toString().trim();
                if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
                    thisLoginEntity = thisQuiz.getTeam(teamNr);
                } else {
                    thisLoginEntity = thisQuiz.getOrganizer(teamNr);
                }
                if (input.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_LoginMain.this, "Please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                } else {
                    //If the correct password was entered
                    if (input.equals(thisLoginEntity.getPasskey())) {
                        //If this is a participant or a corrector or the setFields team
                        if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_PARTICIPANT) ||
                                (thisLoginEntity.getType().equals(LoginEntity.SELECTION_CORRECTOR)) || (thisLoginEntity.getType().equals(LoginEntity.SELECTION_TESTTEAM))) {
                            Intent intent = new Intent(B_LoginMain.this, C_ParticipantHome.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            startActivity(intent);
                        }
                        //If this is a quizmaster
                            if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_QUIZMASTER)) {
                            Intent intent = new Intent(B_LoginMain.this, C_QuizmasterHome.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            startActivity(intent);
                        }
                        //If this is a receptionist
                        if (thisLoginEntity.getType().equals("Receptionist")) {
                            Intent intent = new Intent(B_LoginMain.this, C_EditOrShowTeams.class);
                            thisQuiz.setMyLoginentity(thisLoginEntity);
                            startActivity(intent);
                        }
                    } else {
                    //If the wrong password was entered
                        Toast.makeText(B_LoginMain.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        onItemSelected(0);
    }
/*
    //NEW
    @Override
    public void setFields(String name, String id) {

            b_frag_loginEntity.setFields(name, id);


    }
    +*/
//ENDNEW
    @Override
    public void onItemSelected(int index) {

        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
            tvDisplayName.setText(thisQuiz.getTeams().get(index).getName());
            tvDisplayID.setText(Integer.toString(thisQuiz.getTeams().get(index).getId()));

            //tFields(thisQuiz.getTeams().get(index).getName(), Integer.toString(thisQuiz.getTeams().get(index).getId()));
        } else {
            //setFields(thisQuiz.getOrganizers().get(index).getName(), Integer.toString(thisQuiz.getOrganizers().get(index).getId()));
            tvDisplayName.setText(thisQuiz.getOrganizers().get(index).getName());
            tvDisplayID.setText(Integer.toString(thisQuiz.getOrganizers().get(index).getId()));
        }
    }
}
