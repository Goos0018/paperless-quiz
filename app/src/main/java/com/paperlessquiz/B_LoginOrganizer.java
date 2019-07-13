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
import com.paperlessquiz.loginentity.Organizer;
import com.paperlessquiz.loginentity.Team;
import com.paperlessquiz.loginentity.User;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizGenerator;

/**
 * Main login screen for users, allows user to select a team to log in and proceed to the Participant Home screen
 */
public class B_LoginOrganizer extends AppCompatActivity {
    Quiz thisQuiz = MyApplication.theQuiz;
    Organizer thisOrganizer;
    //Local items in interface
    TextView tvLoginPrompt, tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    ListView lvShowParticipants;
    ParticipantsAdapter adapter;
    String loginType;
    //other local variables needed
    int organizerNr;

    private void setFields(int position) {
        tvDisplayName.setText(thisQuiz.getOrganizers().get(position).getOrganizerType() + "");
        tvDisplayID.setText(Integer.toString(thisQuiz.getOrganizers().get(position).getOrganizerNr()));
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
        loginType = getIntent().getStringExtra(Team.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);
        if (loginType.equals(QuizGenerator.SELECTION_PARTICIPANT)) {
            adapter = new ParticipantsAdapter(this, thisQuiz.convertTeamToUserArray(thisQuiz.getTeams()));
            tvLoginPrompt.setText(this.getString(R.string.main_selectteamprompt));
        } else {
            adapter = new ParticipantsAdapter(this, thisQuiz.convertOrganizerToUserArray(thisQuiz.getOrganizers()));
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
                organizerNr = Integer.valueOf(tvDisplayID.getText().toString());
                String input = etPasskey.getText().toString().trim();
                thisOrganizer = thisQuiz.getOrganizer(organizerNr);
                if (input.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_LoginOrganizer.this, B_LoginOrganizer.this.getString(R.string.main_wrongpswdentered), Toast.LENGTH_SHORT).show();
                } else {
                    //If the correct password was entered
                    if (input.equals(thisOrganizer.getPasskey())) {
                        //If this is a participant or a corrector or the setFields team
                        thisQuiz.setThisOrganizer(thisOrganizer);
                        switch (thisOrganizer.getOrganizerType()) {
                            case QuizDatabase.ORGANIZERTYPE_QUIZMASTER:
                                Intent intentQM = new Intent(B_LoginOrganizer.this, C_QuizmasterRounds.class);
                                startActivity(intentQM);
                                break;
                            case QuizDatabase.ORGANIZERTYPE_CORRECTOR:
                                Intent intentC = new Intent(B_LoginOrganizer.this, C_CorrectorHome.class);
                                startActivity(intentC);
                                break;
                            case QuizDatabase.ORGANIZERTYPE_RECEPTIONIST:
                                Intent intentR = new Intent(B_LoginOrganizer.this, C_ReceptionistHome.class);
                                startActivity(intentR);
                                break;
                        }
                    } else {
                        //If the wrong password was entered
                        Toast.makeText(B_LoginOrganizer.this, B_LoginOrganizer.this.getString(R.string.main_wrongpassword), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
