package com.example.paperlessquiz;

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


public class C_LogInToQuiz extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEntity;

    // TODO: pass quiz docID and load data from the real quiz (including logins and teams
    // TODO: update layout to be able to select a team or role and actually log in if the quiz allow this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_to_quiz);

        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra("thisQuizBasics");
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra("thisQuizExtras");
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra("thisLoginEntity");
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
                        Toast.makeText(C_LogInToQuiz.this, "Successful login", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(C_LogInToQuiz.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                Intent intent = new Intent(com.example.paperlessquiz.MainActivity.this, B_SelectLoginName.class);
                intent.putExtra("thisQuizBasics", thisQuizBasics);
                intent.putExtra("thisQuizExtras", thisQuizExtras);
                intent.putExtra("thisLoginType", "Organizer");
                startActivity(intent);
                */




            }
        });

    }
}
