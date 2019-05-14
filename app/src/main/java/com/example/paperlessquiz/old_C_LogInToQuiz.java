package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.google.access.EventLogger;
import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessAddLine;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quizlistdata.QuizListData;
import com.example.paperlessquiz.quizextradata.QuizExtraData;

import java.util.Date;


public class old_C_LogInToQuiz extends AppCompatActivity {

    QuizExtraData thisQuizExtraData;
    QuizListData thisQuizListData;
    LoginEntity thisLoginEntity;
    boolean submitPressed = false;
    boolean loginCompleted = false;

    // TODO: pass quiz docID and load data from the real quiz (including logins and teams
    // TODO: update layout to be able to select a team or role and actually log in if the quiz allow this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_log_in_to_quiz);

        thisQuizListData = (QuizListData)getIntent().getSerializableExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS);
        thisQuizExtraData = (QuizExtraData)getIntent().getSerializableExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS);
        thisLoginEntity = (LoginEntity)getIntent().getSerializableExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY);
        String LoginString = "Welcome " + thisLoginEntity.getName() + " to our quiz " + thisQuizListData.getName() +
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
                    Toast.makeText(old_C_LogInToQuiz.this, "Please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (input.equals(thisLoginEntity.getPasskey()))
                    {
                        if (thisLoginEntity.getType().equals(LoginEntity.SELECTION_PARTICIPANT)) {
                            //If it is a participant, start the Overview screen
                            //First log that the user is logging in
                            //This part is used to log whenever the user exits the app when he is not supposed to do so
                            Date now = new Date();
                            String strToday = now.toString();
                            String scriptParams= GoogleAccess.PARAMNAME_DOC_ID + thisQuizListData.getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                                    GoogleAccess.PARAMNAME_SHEET + "TeamRegistration" + GoogleAccess.PARAM_CONCATENATOR +
                                    "LineToAdd=[\"" + strToday + "\",\"" + thisLoginEntity.getName() + "\",\"logged in\"]" +  GoogleAccess.PARAM_CONCATENATOR +
                                    GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_ADDLINE;
                            GoogleAccessAddLine teamLogIn = new GoogleAccessAddLine(old_C_LogInToQuiz.this, scriptParams );
                            teamLogIn.addLine();
                            submitPressed = true;
                            loginCompleted=true;
                            Intent intent = new Intent(old_C_LogInToQuiz.this, D_PA_ShowRounds.class);
                            intent.putExtra(QuizListData.INTENT_EXTRA_NAME_THIS_QUIZ_BASICS, thisQuizListData);
                            intent.putExtra(QuizExtraData.INTENT_EXTRA_NAME_THIS_QUIZ_EXTRAS, thisQuizExtraData);
                            intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY, thisLoginEntity);
                            startActivity(intent);
                        }
                        else
                        {
                            //TODO implement other options
                            Toast.makeText(old_C_LogInToQuiz.this, thisLoginEntity.getType() + " still to implement", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(old_C_LogInToQuiz.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    @Override
    public void onPause()
    {
        //if you are exiting this screen without pressing submit, log this event
        if (!submitPressed && loginCompleted) {
            EventLogger logger = new EventLogger(old_C_LogInToQuiz.this, thisQuizListData.getSheetDocID(), GoogleAccess.SHEET_TEAMCONTROL);
            logger.logEvent(thisLoginEntity.getName(),"Exited the app");
        }
        super.onPause();
        submitPressed=false; //reset this each time
    }
}
