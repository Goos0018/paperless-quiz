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
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;
import com.example.paperlessquiz.quizextras.QuizExtrasParser;


public class C_LogInToQuiz extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEntity;

    // TODO: pass quiz docID and load data from the real quiz (including logins and teams
    // TODO: update layout to be able to select a team or role and actually log in if the quiz allow this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_log_in_to_quiz);

        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        String LoginString = "Welcome " + thisLoginEntity.getName() + " to our quiz " + thisQuizBasics.getName() +
                ". Please enter your passkey" + "(Name: " + thisLoginEntity.getName() + ". Passkey: " + thisLoginEntity.getPasskey() + ")";
        TextView tv_Name = findViewById(R.id.tv_name) ;
        TextView tv_LoginPrompt = findViewById(R.id.tv_login_prompt) ;
        EditText et_Passkey = findViewById(R.id.et_passkey);
        tv_LoginPrompt.setText(LoginString);
        Button btn_SubmitLogin = findViewById(R.id.btn_submit_login);
        btn_SubmitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et_Passkey.getText().toString().trim();
                if (input.isEmpty())
                {
                    Toast.makeText(C_LogInToQuiz.this, "Please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (input.equals(thisLoginEntity.getPasskey()))
                    {
                        if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_PARTICIPANT)) {
                            //If it is a participant, start the Overview screen
                            //Toast.makeText(C_LogInToQuiz.this, "Successful login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(C_LogInToQuiz.this, DPA_ShowQuizOverview.class);
                            intent.putExtra(QuizBasics.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizBasics);
                            intent.putExtra(QuizExtrasParser.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtras);
                            intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY, thisLoginEntity);
                            startActivity(intent);
                        }
                        else
                        {
                            //TODO implement other options
                            Toast.makeText(C_LogInToQuiz.this, thisLoginEntity.getType() + " still to implement", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(C_LogInToQuiz.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
