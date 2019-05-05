package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quizdetails.QuizDetails;
import com.example.paperlessquiz.quizbasics.QuizBasics;
import com.example.paperlessquiz.quizextras.QuizExtras;


public class LogInToQuiz extends AppCompatActivity {

    QuizExtras thisQuizExtras;
    QuizBasics thisQuizBasics;
    LoginEntity thisLoginEnity;

    // TODO: pass quiz docID and load data from the real quiz (including logins and teams
    // TODO: update layout to be able to select a team or role and actually log in if the quiz allow this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_to_quiz);

        thisQuizBasics = (QuizBasics)getIntent().getSerializableExtra("thisQuizBasics");
        thisQuizExtras = (QuizExtras)getIntent().getSerializableExtra("thisQuizExtras");
        thisLoginEnity = (LoginEntity)getIntent().getSerializableExtra("thisLoginEntity");
        String LoginString = "Welcome " + thisLoginEnity.getName() + " to our quiz " + thisQuizBasics.getName() + ". Please enter your passkey";
        TextView tv_Name = findViewById(R.id.tv_name) ;
        TextView tv_LoginPrompt = findViewById(R.id.tv_login_prompt) ;
        tv_LoginPrompt.setText(LoginString);
        Button btn_SubmitLogin = findViewById(R.id.btn_submit_login);
        btn_SubmitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.paperlessquiz.MainActivity.this, SelectLoginName.class);
                intent.putExtra("thisQuizBasics", thisQuizBasics);
                intent.putExtra("thisQuizExtras", thisQuizExtras);
                intent.putExtra("thisLoginType", "Organizer");
                startActivity(intent);




            }
        });
        textView2.setText("About to load data for " + thisQuizBasics.getName() + " from sheet " + thisQuizBasics.getSheetDocID());
        /*GoogleAccessGet<QuizDetails> googleAccessGet = new GoogleAccessGet<QuizDetails>(this, scriptParams);
        googleAccessGet.getItems(new QuizExtrasParser()), new UpdateQuizBasicsObjectLPL(thisQuizBasics),
                new LoadingListenerImpl(this, "Please wait", "Loading quiz", "Something went wrong: "));
        textView2.setText("Loaded data for " + thisQuizDetails.getDescription() + " from sheet " + thisQuizBasics.getSheetDocID());
        */
    }
}
