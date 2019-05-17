package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.google.access.GoogleAccess;
import com.example.paperlessquiz.google.access.GoogleAccessAddLine;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

import java.util.Date;

public class B_LoginMain extends AppCompatActivity implements B_frag_ListEntities.ItemSelected {
    //Extra objects from intent
    Quiz thisQuiz;
    LoginEntity thisLoginEntity;
    //Local items in interface
    TextView tvDisplayName,tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    //other local variables needed
    String id;
    boolean submitPressed = false;
    boolean loginCompleted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_act_login_main);

        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvDisplayID = findViewById(R.id.tvDisplayID);
        btnSubmit = (Button) findViewById(R.id.btn_submit_login);
        etPasskey = (EditText) findViewById(R.id.et_passkey);
        thisQuiz = (Quiz) getIntent().getSerializableExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ);
        String type = (String) getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        onItemSelected(0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = tvDisplayID.getText().toString();
                String input = etPasskey.getText().toString().trim();
                if (type.equals(LoginEntity.SELECTION_PARTICIPANT)){
                    thisLoginEntity = thisQuiz.getTeam(id);
                }
                else
                {
                    thisLoginEntity = thisQuiz.getOrganizer(id);
                }
                if (input.isEmpty())
                {
                    Toast.makeText(B_LoginMain.this, "Please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
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
                            String scriptParams= GoogleAccess.PARAMNAME_DOC_ID + thisQuiz.getListData().getSheetDocID() + GoogleAccess.PARAM_CONCATENATOR +
                                    GoogleAccess.PARAMNAME_SHEET + GoogleAccess.SHEET_TEAMCONTROL + GoogleAccess.PARAM_CONCATENATOR +
                                    "LineToAdd=[\"" + strToday + "\",\"" + thisLoginEntity.getName() + "\",\"logged in\"]" +  GoogleAccess.PARAM_CONCATENATOR +
                                    GoogleAccess.PARAMNAME_ACTION + GoogleAccess.PARAMVALUE_ADDLINE;
                            GoogleAccessAddLine teamLogIn = new GoogleAccessAddLine(B_LoginMain.this, scriptParams );
                            teamLogIn.addLine();
                            submitPressed = true;
                            loginCompleted=true;
                            Intent intent = new Intent(B_LoginMain.this, C_ParticipantHome.class);
                            //Intent intent = new Intent(B_LoginMain.this, old_D_PA_ShowRounds.class);
                            intent.putExtra(Quiz.INTENT_EXTRANAME_THIS_QUIZ, thisQuiz);
                            intent.putExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_ENTITY, thisLoginEntity);
                            startActivity(intent);
                        }
                        else
                        {
                            //TODO implement other options
                            Toast.makeText(B_LoginMain.this, thisLoginEntity.getType() + " still to implement", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(B_LoginMain.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onItemSelected(int index) {
        tvDisplayName.setText(thisQuiz.getTeamsList().get(index).getName());
        tvDisplayID.setText(thisQuiz.getTeamsList().get(index).getId());
    }
}
