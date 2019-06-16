package com.example.paperlessquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paperlessquiz.adapters.ParticipantsAdapter;
import com.example.paperlessquiz.loginentity.LoginEntity;
import com.example.paperlessquiz.quiz.Quiz;

//TODO:prevent showing keyboard initially, only show after selection is made
public class B_LoginMain extends AppCompatActivity {
    Quiz thisQuiz = MyApplication.theQuiz;
    LoginEntity thisLoginEntity;
    //Local items in interface
    TextView tvLoginPrompt, tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    ListView lvShowParticipants;
    ParticipantsAdapter adapter;
    String loginType;
    //other local variables needed
    int teamNr;

    private void setFields(int position) {
        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
            tvDisplayName.setText(thisQuiz.getTeams().get(position).getName());
            tvDisplayID.setText(Integer.toString(thisQuiz.getTeams().get(position).getId()));
            etPasskey.setText("");
            //tFields(thisQuiz.getTeams().get(index).getName(), Integer.toString(thisQuiz.getTeams().get(index).getId()));
        } else {
            //setFields(thisQuiz.getOrganizers().get(index).getName(), Integer.toString(thisQuiz.getOrganizers().get(index).getId()));
            tvDisplayName.setText(thisQuiz.getOrganizers().get(position).getType());
            tvDisplayID.setText(Integer.toString(thisQuiz.getOrganizers().get(position).getId()));
            //tvDisplayID.setVisibility(View.GONE);
            etPasskey.setText("");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_b_login_main);

        tvLoginPrompt = findViewById(R.id.tvLoginPrompt);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvDisplayID = findViewById(R.id.tvDisplayID);
        btnSubmit = (Button) findViewById(R.id.btn_submit_login);
        etPasskey = (EditText) findViewById(R.id.et_passkey);
        lvShowParticipants = (ListView) findViewById(R.id.lvNamesList);
        loginType = (String) getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        if (loginType.equals(LoginEntity.SELECTION_PARTICIPANT)) {
            adapter = new ParticipantsAdapter(this, thisQuiz.getTeams());
            tvLoginPrompt.setText("Select your team in the list below");
        } else {
            adapter = new ParticipantsAdapter(this, thisQuiz.getOrganizers());
            tvLoginPrompt.setText("Select your role in the list below");
        }
        setFields(0);
        lvShowParticipants.setAdapter(adapter);
        lvShowParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setFields(position);
            }
        });

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
                        thisQuiz.setMyLoginentity(thisLoginEntity);
                        switch (thisLoginEntity.getType()) {
                            case LoginEntity.SELECTION_PARTICIPANT:
                                if (thisLoginEntity.isPresent()) {
                                    Intent intentP = new Intent(B_LoginMain.this, C_ParticipantHome.class);
                                    startActivity(intentP);
                                } else {
                                    Toast.makeText(B_LoginMain.this, "Please register at the reception first", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case LoginEntity.TYPE_QUIZMASTER:
                                Intent intentQM = new Intent(B_LoginMain.this, C_QuizmasterRounds.class);
                                startActivity(intentQM);
                                break;
                            case LoginEntity.TYPE_CORRECTOR:
                                Intent intentC = new Intent(B_LoginMain.this, C_CorrectorHome.class);
                                startActivity(intentC);
                                break;
                            case LoginEntity.TYPE_RECEPTIONIST:
                                Intent intentR = new Intent(B_LoginMain.this, C_ReceptionistHome.class);
                                startActivity(intentR);
                                break;
                        }
                    } else {
                        //If the wrong password was entered
                        Toast.makeText(B_LoginMain.this, "Passkey " + input + " is incorrect - please enter the passkey provided by the organizers", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
