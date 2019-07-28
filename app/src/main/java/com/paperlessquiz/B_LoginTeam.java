package com.paperlessquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.paperlessquiz.adapters.ParticipantsAdapter;
import com.paperlessquiz.googleaccess.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.users.Team;
import com.paperlessquiz.quiz.Quiz;

/**
 * Main login screen for users, allows user to select a team to log in and proceed to the Participant Home screen after authentication
 */
public class B_LoginTeam extends AppCompatActivity implements LoadingActivity {
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
    QuizLoader quizLoader;
    String password;
    boolean roundsLoaded, questionsLoaded, answersLoaded;

    @Override
    public void loadingComplete(int callerID) {
        switch (callerID) {
            case QuizDatabase.REQUEST_ID_AUTHENTICATE:
                if (quizLoader.authenticateRequest.isRequestOK()) {
                    thisTeam.setUserPassword(password);
                    thisQuiz.setThisTeam(thisTeam);
                    //Load the rest of the quiz
                    quizLoader.loadQuizForuser(thisTeam.getIdUser(), password, thisQuiz.getListData().getIdQuiz());
                } else {
                    //Authentication failed
                    Toast.makeText(B_LoginTeam.this, B_LoginTeam.this.getString(R.string.main_wrongpassword), Toast.LENGTH_SHORT).show();
                }
                break;

            case QuizDatabase.REQUEST_ID_GET_ROUNDS:
                roundsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_QUESTIONS:
                questionsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ANSWERS:
                answersLoaded = true;
                break;
        }
        if (roundsLoaded && questionsLoaded && answersLoaded) {
            quizLoader.loadRoundsIntoQuiz();
            quizLoader.loadQuestionsIntoQuiz();
            quizLoader.loadMyAnswersIntoQuiz();
            Intent intentP = new Intent(B_LoginTeam.this, C_ParticipantHome.class);
            startActivity(intentP);
        }
    }


    private void setFields(int position) {
        tvDisplayName.setText(thisQuiz.getTeams().get(position).getName());
        tvDisplayID.setText(thisQuiz.getTeams().get(position).getDescription());
        etPasskey.setText("");
        teamNr = thisQuiz.getTeams().get(position).getUserNr();
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
                //Dismiss the keyboard if its there
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPasskey.getWindowToken(), 0);
                password = etPasskey.getText().toString().trim();
                thisTeam = thisQuiz.getTeam(teamNr);
                if (password.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_LoginTeam.this, B_LoginTeam.this.getString(R.string.main_wrongpswdentered), Toast.LENGTH_SHORT).show();
                } else {
                    if (thisTeam.getUserStatus() == QuizDatabase.USERSTATUS_NOTPRESENT) {
                        Toast.makeText(B_LoginTeam.this, B_LoginTeam.this.getString(R.string.main_registeratreceptionfirst), Toast.LENGTH_LONG).show();
                    } else {
                        quizLoader.authenticateUser(thisTeam.getIdUser(), password);
                    }
                }
            }
        });
    }
}
