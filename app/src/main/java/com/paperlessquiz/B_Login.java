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
import com.paperlessquiz.loadinglisteners.LoadingActivity;
import com.paperlessquiz.quiz.QuizDatabase;
import com.paperlessquiz.quiz.QuizLoader;
import com.paperlessquiz.quiz.Quiz;
import com.paperlessquiz.users.User;

import java.util.ArrayList;

/**
 * Main login screen, allows user to select a team to log in and proceed to their Home screen after authentication
 * After this screen is done (and authentication was successful), thisQuiz contains listData, userslist, Rounds, Questions, OrderItems and answers + scores for Round 1
 */
public class B_Login extends AppCompatActivity implements LoadingActivity {
    Quiz thisQuiz = MyApplication.theQuiz;
    User thisUser;
    boolean isOrganizer;
    ArrayList<User> userList;
    String loginPrompt;
    //Local items in interface
    TextView tvLoginPrompt, tvDisplayName, tvDisplayID;
    EditText etPasskey;
    Button btnSubmit;
    ListView lvShowUsers;
    ParticipantsAdapter adapter;
    //other local variables needed
    int userNr;
    QuizLoader quizLoader;
    String password;
    boolean roundsLoaded, questionsLoaded, orderItemsLoaded, answersLoaded, scoresLoaded; //False by default
    Intent intent;

    @Override
    public void loadingComplete(int requestId) {
        switch (requestId) {
            case QuizDatabase.REQUEST_ID_AUTHENTICATE:
                if (quizLoader.authenticateRequest.isRequestOK()) {
                    thisUser.setUserPassword(password);
                    thisQuiz.setThisUser(thisUser);
                    //Load what is needed (too much for some users but anyway...)
                    quizLoader.loadRounds();
                    quizLoader.loadQuestions();
                    quizLoader.loadOrderItems();
                    quizLoader.loadMyAnswers(1);
                    quizLoader.loadScoresAndStandings(1);
                    //Do the rest when all of these requests are complete
                } else {
                    //Authentication failed - error already shown by the submit activity
                    //MyApplication.showError(this, B_Login.this.getString(R.string.main_wrongpassword));
                    //Toast.makeText(B_Login.this, B_Login.this.getString(R.string.main_wrongpassword), Toast.LENGTH_SHORT).show();
                }
                break;
            case QuizDatabase.REQUEST_ID_GET_ROUNDS:
                roundsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_QUESTIONS:
                questionsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ORDERITEMS:
                orderItemsLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_ANSWERS:
                answersLoaded = true;
                break;
            case QuizDatabase.REQUEST_ID_GET_SCORES:
                scoresLoaded = true;
                break;
        }
        //Only if everything is properly loaded, we can start populating the central Quiz object
        if (roundsLoaded && questionsLoaded && answersLoaded && scoresLoaded && orderItemsLoaded) {
            //reset the loading statuses
            roundsLoaded = false;
            questionsLoaded = false;
            answersLoaded = false;
            scoresLoaded = false;
            orderItemsLoaded = false;
            quizLoader.loadRoundsIntoQuiz();
            quizLoader.loadQuestionsIntoQuiz();
            //Make sure we have answers for all teams and all questions before we start setting the answers
            thisQuiz.initializeAllAnswers();
            quizLoader.updateAnswersIntoQuiz();
            quizLoader.loadResultsIntoQuiz();
            quizLoader.loadOrderItemsIntoQuiz();
            //Now open the appropriate home screen

            switch (thisUser.getUserType()) {
                case QuizDatabase.USERTYPE_TEAM:
                    intent = new Intent(B_Login.this, C_ParticipantHome.class);
                    startActivity(intent);
                    break;
                case QuizDatabase.USERTYPE_QUIZMASTER:
                    intent = new Intent(B_Login.this, C_QuizmasterHome.class);
                    startActivity(intent);
                    break;
                case QuizDatabase.USERTYPE_CORRECTOR:
                    intent = new Intent(B_Login.this, C_CorrectorHome.class);
                    startActivity(intent);
                    break;
                case QuizDatabase.USERTYPE_JUROR:
                    intent = new Intent(B_Login.this, C_JurorHome.class);
                    startActivity(intent);
                    break;
                case QuizDatabase.USERTYPE_RECEPTIONIST:
                    intent = new Intent(B_Login.this, C_ReceptionistHome.class);
                    startActivity(intent);
                    break;
                case QuizDatabase.USERTYPE_BARRESPONSIBLE:
                    intent = new Intent(B_Login.this, C_BarResponsibleHome.class);
                    startActivity(intent);
                    break;
                case QuizDatabase.USERTYPE_BARHELPER:
                    intent = new Intent(B_Login.this, C_BarHelperHome.class);
                    startActivity(intent);
                    break;
            }
        }
    }


    private void setFields(int position) {
        if (isOrganizer) {
            tvDisplayName.setText(thisQuiz.getOrganizers().get(position).getName());
            tvDisplayID.setText(thisQuiz.getOrganizers().get(position).getDescription());
            etPasskey.setText("");
            userNr = thisQuiz.getOrganizers().get(position).getUserNr();
        } else {
            tvDisplayName.setText(thisQuiz.getTeams().get(position).getName());
            tvDisplayID.setText(thisQuiz.getTeams().get(position).getDescription());
            etPasskey.setText("");
            userNr = thisQuiz.getTeams().get(position).getUserNr();
        }
        //Show the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etPasskey,0); //     hideSoftInputFromWindow(etPasskey.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_b_login);
        //Get stuff from the interface
        tvLoginPrompt = findViewById(R.id.tvLoginPrompt);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        tvDisplayID = findViewById(R.id.tvDisplayID);
        btnSubmit = findViewById(R.id.btn_submit_login);
        etPasskey = findViewById(R.id.et_passkey);
        lvShowUsers = findViewById(R.id.lvNamesList);
        //All the rest
        isOrganizer = getIntent().getBooleanExtra(QuizDatabase.INTENT_EXTRANAME_IS_ORGANIZER, false);
        quizLoader = new QuizLoader(this);
        if (isOrganizer) {
            userList = thisQuiz.convertOrganizerToUserArray(thisQuiz.getOrganizers());
            loginPrompt = this.getString(R.string.main_selectorganizerprompt);
        } else {
            userList = thisQuiz.convertTeamToUserArray(thisQuiz.getTeams());
            loginPrompt = this.getString(R.string.main_selectteamprompt);
        }
        adapter = new ParticipantsAdapter(this, userList);
        tvLoginPrompt.setText(loginPrompt);
        setFields(0);
        lvShowUsers.setAdapter(adapter);
        lvShowUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setFields(position);
            }
        });
        //Login button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dismiss the keyboard if its there
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPasskey.getWindowToken(), 0);
                password = etPasskey.getText().toString().trim();
                if (password.isEmpty()) {
                    //If no password was entered
                    Toast.makeText(B_Login.this, B_Login.this.getString(R.string.main_wrongpswdentered), Toast.LENGTH_SHORT).show();
                } else {
                    if (isOrganizer) {
                        thisUser = (User) thisQuiz.getOrganizer(userNr);
                        quizLoader.authenticateUser(thisUser.getIdUser(), password);
                        //The rest is done when loading is complete
                    } else {
                        thisUser = (User) thisQuiz.getTeam(userNr);
                        if (thisUser.getUserStatus() == QuizDatabase.USERSTATUS_NOTPRESENT) {
                            Toast.makeText(B_Login.this, B_Login.this.getString(R.string.main_registeratreceptionfirst), Toast.LENGTH_LONG).show();
                            intent = new Intent(B_Login.this, A_Main.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            quizLoader.authenticateUser(thisUser.getIdUser(), password);
                            //The rest is done when loading is complete
                        }
                    }
                }
            }
        });
    }
}
