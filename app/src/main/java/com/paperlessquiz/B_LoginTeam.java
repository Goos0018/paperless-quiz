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

import com.paperlessquiz.adapters.ParticipantsAdapter;
import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.quiz.Quiz;

/**
 * Main login screen for users, allows user to select a team to log in and proceed to the Participant Home screen
 */
public class B_LoginTeam extends AppCompatActivity {
    Quiz thisQuiz = MyApplication.theQuiz;
    Team thisTeam;
    //Local items in interface
    TextView tvLoginPrompt, tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    ListView lvShowParticipants;
    ParticipantsAdapter adapter;
    //other local variables needed
    int teamNr;

    private void setFields(int position) {
        tvDisplayName.setText(thisQuiz.getTeams().get(position).getName());
        tvDisplayID.setText(Integer.toString(thisQuiz.getTeams().get(position).getTeamNr()));
        etPasskey.setText("");
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
        adapter = new ParticipantsAdapter(this, thisQuiz.convertTeamToUserArray(thisQuiz.getTeams()));
        tvLoginPrompt.setText(this.getString(R.string.main_selectteamprompt));

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
                thisTeam = thisQuiz.getTeam(teamNr);
                if (input.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_LoginTeam.this, B_LoginTeam.this.getString(R.string.main_wrongpswdentered), Toast.LENGTH_SHORT).show();
                } else {
                    //If the correct password was entered
                    if (input.equals(thisTeam.getPasskey())) {
                        //If this is a participant or a corrector or the setFields team
                        thisQuiz.setThisTeam(thisTeam);
                        if (thisTeam.isPresent()) {
                            Intent intentP = new Intent(B_LoginTeam.this, C_ParticipantHome.class);
                            startActivity(intentP);
                        } else {
                            Toast.makeText(B_LoginTeam.this, B_LoginTeam.this.getString(R.string.main_registeratreceptionfirst), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        //If the wrong password was entered
                        Toast.makeText(B_LoginTeam.this, B_LoginTeam.this.getString(R.string.main_wrongpassword), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
