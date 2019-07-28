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
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.users.Organizer;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizGenerator;

/**
 * Main login screen for users, allows user to select a team to log in and proceed to the Participant Home screen
 */
public class B_LoginOrganizer extends AppCompatActivity implements LoadingActivity {
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
    QuizLoader quizLoader;

    @Override
    public void loadingComplete(int callerID) {
        switch (callerID) {
            case QuizDatabase.REQUEST_ID_AUTHENTICATE:
                if (quizLoader.authenticateRequest.isRequestOK()) {
                    thisQuiz.setThisOrganizer(thisOrganizer);
                    switch (thisOrganizer.getUserType()) {
                        case QuizDatabase.USERTYPE_QUIZMASTER:
                            Intent intentQM = new Intent(B_LoginOrganizer.this, C_QuizmasterRounds.class);
                            startActivity(intentQM);
                            break;
                        case QuizDatabase.USERTYPE_CORRECTOR:
                            Intent intentC = new Intent(B_LoginOrganizer.this, C_CorrectorHome.class);
                            startActivity(intentC);
                            break;
                        case QuizDatabase.USERTYPE_RECEPTIONIST:
                            Intent intentR = new Intent(B_LoginOrganizer.this, C_ReceptionistHome.class);
                            startActivity(intentR);
                            break;
                    }
                } else {
                    //Authentication failed
                    Toast.makeText(B_LoginOrganizer.this, B_LoginOrganizer.this.getString(R.string.main_wrongpassword), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setFields(int position) {
        tvDisplayName.setText(thisQuiz.getOrganizers().get(position).getName());
        tvDisplayID.setText(thisQuiz.getOrganizers().get(position).getDescription());
        etPasskey.setText("");
        organizerNr = thisQuiz.getOrganizers().get(position).getUserNr();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_b_login_main);
        quizLoader = new QuizLoader(this);

        tvLoginPrompt = findViewById(R.id.tvLoginPrompt);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvDisplayID = findViewById(R.id.tvDisplayID);
        btnSubmit = findViewById(R.id.btn_submit_login);
        etPasskey = findViewById(R.id.et_passkey);
        lvShowParticipants = findViewById(R.id.lvNamesList);
        loginType = getIntent().getStringExtra(Team.INTENT_EXTRA_NAME_THIS_LOGIN_TYPE);

        adapter = new ParticipantsAdapter(this, thisQuiz.convertOrganizerToUserArray(thisQuiz.getOrganizers()));
        tvLoginPrompt.setText(this.getString(R.string.main_selectorganizerprompt));

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
                String input = etPasskey.getText().toString().trim();
                thisOrganizer = thisQuiz.getOrganizer(organizerNr);
                if (input.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_LoginOrganizer.this, B_LoginOrganizer.this.getString(R.string.main_wrongpswdentered), Toast.LENGTH_SHORT).show();
                } else {
                   quizLoader.authenticateUser(thisOrganizer.getIdUser(),input);
                }
            }
        });
    }
}
