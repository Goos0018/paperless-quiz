package com.paperlessquiz;

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

import com.paperlessquiz.R;
import com.paperlessquiz.adapters.ParticipantsAdapter;
import com.paperlessquiz.loginentity.LoginEntity;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizGenerator;

/**
 * Main login screen, allows user to select a team or organizer role and log in
 * Depending on the role (team, quizmaster, corrector, ... , the appropriate home screen will be opened
 */
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
        if (loginType.equals(QuizGenerator.SELECTION_PARTICIPANT)) {
            tvDisplayName.setText(thisQuiz.getTeams().get(position).getName());
            tvDisplayID.setText(Integer.toString(thisQuiz.getTeams().get(position).getId()));
            etPasskey.setText("");
        } else {
            tvDisplayName.setText(thisQuiz.getOrganizers().get(position).getType());
            tvDisplayID.setText(Integer.toString(thisQuiz.getOrganizers().get(position).getId()));
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
        btnSubmit = findViewById(R.id.btn_submit_login);
        etPasskey = findViewById(R.id.et_passkey);
        lvShowParticipants = findViewById(R.id.lvNamesList);
        loginType = getIntent().getStringExtra(LoginEntity.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        if (loginType.equals(QuizGenerator.SELECTION_PARTICIPANT)) {
            adapter = new ParticipantsAdapter(this, thisQuiz.getTeams());
            tvLoginPrompt.setText(this.getString(R.string.main_selectteamprompt));
        } else {
            adapter = new ParticipantsAdapter(this, thisQuiz.getOrganizers());
            tvLoginPrompt.setText(this.getString(R.string.main_selectorganizerprompt));
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
                if (loginType.equals(QuizGenerator.SELECTION_PARTICIPANT)) {
                    thisLoginEntity = thisQuiz.getTeam(teamNr);
                } else {
                    thisLoginEntity = thisQuiz.getOrganizer(teamNr);
                }
                if (input.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_LoginMain.this, B_LoginMain.this.getString(R.string.main_wrongpswdentered), Toast.LENGTH_SHORT).show();
                } else {
                    //If the correct password was entered
                    if (input.equals(thisLoginEntity.getPasskey())) {
                        //If this is a participant or a corrector or the setFields team
                        thisQuiz.setMyLoginentity(thisLoginEntity);
                        switch (thisLoginEntity.getType()) {
                            case QuizGenerator.SELECTION_PARTICIPANT:
                                if (thisLoginEntity.isPresent()) {
                                    Intent intentP = new Intent(B_LoginMain.this, C_ParticipantHome.class);
                                    startActivity(intentP);
                                } else {
                                    Toast.makeText(B_LoginMain.this, B_LoginMain.this.getString(R.string.main_registeratreceptionfirst), Toast.LENGTH_LONG).show();
                                }
                                break;
                            case QuizGenerator.TYPE_QUIZMASTER:
                                Intent intentQM = new Intent(B_LoginMain.this, C_QuizmasterRounds.class);
                                startActivity(intentQM);
                                break;
                            case QuizGenerator.TYPE_CORRECTOR:
                                Intent intentC = new Intent(B_LoginMain.this, C_CorrectorHome.class);
                                startActivity(intentC);
                                break;
                            case QuizGenerator.TYPE_RECEPTIONIST:
                                Intent intentR = new Intent(B_LoginMain.this, C_ReceptionistHome.class);
                                startActivity(intentR);
                                break;
                        }
                    } else {
                        //If the wrong password was entered
                        Toast.makeText(B_LoginMain.this, B_LoginMain.this.getString(R.string.main_wrongpassword), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
